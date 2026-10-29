package com.learntrack.data.repository

import android.content.Context
import android.content.res.AssetManager
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.IOException

class CourseRepositoryImplTest {

    private lateinit var context: Context
    private lateinit var assetManager: AssetManager
    private lateinit var repository: CourseRepositoryImpl

    @Before
    fun setUp() {
        context = mockk()
        assetManager = mockk()
        every { context.assets } returns assetManager
        repository = CourseRepositoryImpl(context)
    }

    private fun validJson(count: Int = 3): String {
        val topics = (1..count).joinToString(",") { i ->
            val status = if (i <= 2) "completed" else "not_started"
            """{ "id": "topic_0$i", "name": "Topic $i", "status": "$status" }"""
        }
        return """{"topics":[$topics]}"""
    }

    @Test
    fun `valid JSON loads topics successfully`() = runTest {
        val json = validJson(3)
        every { assetManager.open("learntrack_topics.json") } returns
            ByteArrayInputStream(json.toByteArray())

        val result = repository.getTopics().first()
        assertTrue(result.isSuccess)
        assertEquals(3, result.getOrNull()?.size)
    }

    @Test
    fun `missing file emits Result failure with IOException`() = runTest {
        every { assetManager.open("learntrack_topics.json") } throws
            IOException("File not found")

        val result = repository.getTopics().first()
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IOException)
    }

    @Test
    fun `malformed JSON emits Result failure`() = runTest {
        val bad = "{ this is not valid json }"
        every { assetManager.open("learntrack_topics.json") } returns
            ByteArrayInputStream(bad.toByteArray())

        val result = repository.getTopics().first()
        assertTrue(result.isFailure)
    }
}
