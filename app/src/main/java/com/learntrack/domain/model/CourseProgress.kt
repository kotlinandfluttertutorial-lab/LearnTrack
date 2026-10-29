package com.learntrack.domain.model

/**
 * Domain model representing the computed progress of a course.
 * All values are derived from live data — never hard-coded.
 */
data class CourseProgress(
    val total: Int,
    val completed: Int,
    val notStarted: Int,
    val completionPercentage: Float
)
