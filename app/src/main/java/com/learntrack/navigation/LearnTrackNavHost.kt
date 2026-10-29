package com.learntrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.learntrack.ui.progress.CourseProgressScreenRoute
import com.learntrack.ui.progress.CourseProgressViewModel
import com.learntrack.ui.topics.TopicDetailScreenRoute
import com.learntrack.ui.topics.TopicListScreenRoute

/**
 * The navigation host for the LearnTrack app.
 * All three screens share the same [CourseProgressViewModel] instance
 * to avoid redundant data loads.
 */
@Composable
fun LearnTrackNavHost(
    viewModel: CourseProgressViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CourseProgress.route
    ) {
        composable(Screen.CourseProgress.route) {
            CourseProgressScreenRoute(
                viewModel = viewModel,
                onNavigateToTopics = { navController.navigate(Screen.TopicList.route) }
            )
        }

        composable(Screen.TopicList.route) {
            TopicListScreenRoute(
                viewModel = viewModel,
                onTopicClick = { topicId ->
                    navController.navigate(Screen.TopicDetail.createRoute(topicId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.TopicDetail.ROUTE,
            arguments = listOf(
                navArgument("topicId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
            TopicDetailScreenRoute(
                topicId = topicId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
