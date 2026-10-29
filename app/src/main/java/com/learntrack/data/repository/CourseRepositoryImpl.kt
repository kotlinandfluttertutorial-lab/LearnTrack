package com.learntrack.data.repository

import android.content.Context
import android.util.Log
import com.learntrack.BuildConfig
import com.learntrack.data.model.Topic
import com.learntrack.data.model.TopicContainer
import com.learntrack.data.validation.TopicSchemaValidator
import com.learntrack.data.validation.ValidationException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

private const val TAG = "LearnTrack"
private const val ASSET_FILE = "learntrack_topics.json"

class CourseRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CourseRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override fun getTopics(): Flow<Result<List<Topic>>> = flow {
        try {
            val rawJson = context.assets.open(ASSET_FILE).use { stream ->
                stream.bufferedReader().readText()
            }
            val container = json.decodeFromString<TopicContainer>(rawJson)
            val validated = TopicSchemaValidator.validate(container.topics)
            emit(Result.success(validated))
        } catch (e: IOException) {
            if (BuildConfig.DEBUG) Log.e(TAG, "Failed to open asset: $ASSET_FILE", e)
            emit(Result.failure(e))
        } catch (e: SerializationException) {
            if (BuildConfig.DEBUG) Log.e(TAG, "Failed to parse JSON", e)
            emit(Result.failure(e))
        } catch (e: ValidationException) {
            if (BuildConfig.DEBUG) Log.e(TAG, "JSON schema validation failed", e)
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
