package com.learntrack.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TopicContainer(
    val topics: List<Topic>
)
