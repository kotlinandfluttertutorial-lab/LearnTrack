package com.learntrack.data.repository

import com.learntrack.data.model.Topic
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for course topic data.
 * Implementations read from the bundled JSON asset.
 */
interface CourseRepository {
    fun getTopics(): Flow<Result<List<Topic>>>
}
