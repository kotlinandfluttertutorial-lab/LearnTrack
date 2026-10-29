package com.learntrack.data.validation

import com.learntrack.data.model.Topic

/**
 * Validates a list of [Topic] objects against the LearnTrack schema rules.
 * Throws [ValidationException] on any violation.
 */
object TopicSchemaValidator {

    fun validate(topics: List<Topic>): List<Topic> {
        if (topics.isEmpty()) return topics

        val seenIds = mutableSetOf<String>()
        topics.forEachIndexed { index, topic ->
            if (topic.id.isBlank()) {
                throw ValidationException("Topic at index $index has a blank id")
            }
            if (topic.name.isBlank()) {
                throw ValidationException("Topic '${topic.id}' has a blank name")
            }
            if (!seenIds.add(topic.id)) {
                throw ValidationException("Duplicate topic id: '${topic.id}'")
            }
        }
        return topics
    }
}
