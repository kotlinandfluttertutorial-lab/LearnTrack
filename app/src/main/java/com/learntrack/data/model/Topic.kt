package com.learntrack.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val id: String,
    val name: String,
    @SerialName("status") val status: TopicStatus
)
