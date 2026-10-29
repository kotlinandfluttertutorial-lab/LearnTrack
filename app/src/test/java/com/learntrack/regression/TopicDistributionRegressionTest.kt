package com.learntrack.regression

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

@Serializable
private data class RawTopic(val id: String, val name: String, val status: String)

@Serializable
private data class RawContainer(val topics: List<RawTopic>)

/**
 * Regression test that reads the actual learntrack_topics.json from test resources
 * and asserts the authoritative distribution: 41 total, 22 completed, 19 not_started.
 *
 * THIS TEST MUST FAIL LOUDLY if the distribution drifts.
 */
class TopicDistributionRegressionTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `topic distribution is 22 completed 19 not_started 41 total`() {
        val stream = javaClass.classLoader!!
            .getResourceAsStream("learntrack_topics.json")
            ?: error("learntrack_topics.json not found in test resources. " +
                "Copy it to app/src/test/resources/learntrack_topics.json")

        val rawJson = stream.use { it.bufferedReader().readText() }
        val container = json.decodeFromString<RawContainer>(rawJson)
        val topics = container.topics

        val total = topics.size
        val completed = topics.count { it.status == "completed" }
        val notStarted = topics.count { it.status == "not_started" }

        assertEquals(
            "REGRESSION: Total topic count drifted from 41. " +
                "Do NOT change learntrack_topics.json without explicit approval. Found: $total",
            41, total
        )
        assertEquals(
            "REGRESSION: Completed topic count drifted from 22. " +
                "Do NOT change learntrack_topics.json without explicit approval. Found: $completed",
            22, completed
        )
        assertEquals(
            "REGRESSION: Not-started topic count drifted from 19. " +
                "Do NOT change learntrack_topics.json without explicit approval. Found: $notStarted",
            19, notStarted
        )
    }
}
