package com.learntrack.navigation

/**
 * Sealed class defining all navigation routes in the app.
 */
sealed class Screen(val route: String) {
    object CourseProgress : Screen("course_progress")
    object TopicList : Screen("topic_list")
    object TopicDetail : Screen("topic_detail/{topicId}") {
        const val ROUTE = "topic_detail/{topicId}"
        fun createRoute(topicId: String) = "topic_detail/$topicId"
    }
}
