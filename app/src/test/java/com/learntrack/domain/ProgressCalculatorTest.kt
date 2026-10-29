package com.learntrack.domain

import com.learntrack.data.model.Topic
import com.learntrack.data.model.TopicStatus
import com.learntrack.domain.usecase.CalculateProgressUseCase
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProgressCalculatorTest {

    private lateinit var useCase: CalculateProgressUseCase

    @Before
    fun setUp() {
        useCase = CalculateProgressUseCase()
    }

    private fun makeTopic(id: String, status: TopicStatus) =
        Topic(id = id, name = "Topic $id", status = status)

    @Test
    fun `22 of 41 topics completed returns 53_7 percent`() {
        val topics = (1..22).map { makeTopic("topic_$it", TopicStatus.COMPLETED) } +
            (23..41).map { makeTopic("topic_$it", TopicStatus.NOT_STARTED) }
        val result = useCase(topics)
        assertEquals(53.7f, result.completionPercentage, 0.01f)
        assertEquals(41, result.total)
        assertEquals(22, result.completed)
        assertEquals(19, result.notStarted)
    }

    @Test
    fun `0 of 41 topics completed returns 0_0 percent`() {
        val topics = (1..41).map { makeTopic("topic_$it", TopicStatus.NOT_STARTED) }
        val result = useCase(topics)
        assertEquals(0.0f, result.completionPercentage, 0.001f)
    }

    @Test
    fun `41 of 41 topics completed returns 100_0 percent`() {
        val topics = (1..41).map { makeTopic("topic_$it", TopicStatus.COMPLETED) }
        val result = useCase(topics)
        assertEquals(100.0f, result.completionPercentage, 0.001f)
    }

    @Test
    fun `empty list returns 0_0 percent`() {
        val result = useCase(emptyList())
        assertEquals(0.0f, result.completionPercentage, 0.001f)
        assertEquals(0, result.total)
    }

    @Test
    fun `1 of 2 topics completed returns 50_0 percent`() {
        val topics = listOf(
            makeTopic("t1", TopicStatus.COMPLETED),
            makeTopic("t2", TopicStatus.NOT_STARTED)
        )
        val result = useCase(topics)
        assertEquals(50.0f, result.completionPercentage, 0.001f)
    }
}
