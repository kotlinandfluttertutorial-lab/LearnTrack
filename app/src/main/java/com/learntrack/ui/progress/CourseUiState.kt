package com.learntrack.ui.progress

import com.learntrack.data.model.Topic
import com.learntrack.domain.model.CourseProgress

/**
 * Represents the possible UI states for the course progress screen.
 */
sealed class CourseUiState {
    object Loading : CourseUiState()
    object Empty : CourseUiState()
    data class Success(
        val progress: CourseProgress,
        val topics: List<Topic>
    ) : CourseUiState()
    data class Error(val message: String) : CourseUiState()
}
