package com.learntrack.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TopicStatus {
    @SerialName("completed") COMPLETED,
    @SerialName("not_started") NOT_STARTED
}
