package com.learntrack.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit4.runners.AndroidJUnit4
import com.learntrack.data.model.Topic
import com.learntrack.data.model.TopicStatus
import com.learntrack.domain.model.CourseProgress
import com.learntrack.ui.progress.CourseProgressScreen
import com.learntrack.ui.progress.CourseUiState
import com.learntrack.ui.theme.LearnTrackTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CourseProgressScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun makeTopics(): List<Topic> =
        (1..22).map { Topic("topic_${it}", "Topic $it", TopicStatus.COMPLETED) } +
        (23..41).map { Topic("topic_${it}", "Topic $it", TopicStatus.NOT_STARTED) }

    @Test
    fun progressScreen_showsCompletionPercentage() {
        val topics = makeTopics()
        val progress = CourseProgress(
            total = 41, completed = 22, notStarted = 19,
            completionPercentage = 53.7f
        )
        composeTestRule.setContent {
            LearnTrackTheme {
                CourseProgressScreen(
                    uiState = CourseUiState.Success(progress, topics),
                    onNavigateToTopics = {},
                    onRetry = {}
                )
            }
        }
        composeTestRule.onNodeWithText("53.7%").assertIsDisplayed()
    }

    @Test
    fun progressScreen_showsTopicCount() {
        val topics = makeTopics()
        val progress = CourseProgress(
            total = 41, completed = 22, notStarted = 19,
            completionPercentage = 53.7f
        )
        composeTestRule.setContent {
            LearnTrackTheme {
                CourseProgressScreen(
                    uiState = CourseUiState.Success(progress, topics),
                    onNavigateToTopics = {},
                    onRetry = {}
                )
            }
        }
        // Verify that both 22 and 41 appear in the UI
        composeTestRule.onNodeWithText("22 of 41 topics completed").assertIsDisplayed()
    }

    @Test
    fun progressScreen_showsProgressIndicator() {
        val topics = makeTopics()
        val progress = CourseProgress(
            total = 41, completed = 22, notStarted = 19,
            completionPercentage = 53.7f
        )
        composeTestRule.setContent {
            LearnTrackTheme {
                CourseProgressScreen(
                    uiState = CourseUiState.Success(progress, topics),
                    onNavigateToTopics = {},
                    onRetry = {}
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Course progress: 53.7 percent").assertIsDisplayed()
    }
}
