package com.learntrack.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit4.runners.AndroidJUnit4
import com.learntrack.data.model.Topic
import com.learntrack.data.model.TopicStatus
import com.learntrack.domain.model.CourseProgress
import com.learntrack.ui.progress.CourseUiState
import com.learntrack.ui.topics.TopicListScreen
import com.learntrack.ui.theme.LearnTrackTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TopicListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun makeTopics(): List<Topic> =
        (1..22).map { Topic("topic_${it}", "Topic $it", TopicStatus.COMPLETED) } +
        (23..41).map { Topic("topic_${it}", "Topic $it", TopicStatus.NOT_STARTED) }

    @Test
    fun topicList_showsCompletedBadge() {
        val topics = makeTopics()
        val progress = CourseProgress(41, 22, 19, 53.7f)
        composeTestRule.setContent {
            LearnTrackTheme {
                TopicListScreen(
                    uiState = CourseUiState.Success(progress, topics),
                    onTopicClick = {},
                    onBack = {},
                    onRetry = {}
                )
            }
        }
        // At least one Completed badge is visible
        composeTestRule.onNodeWithText("Completed").assertIsDisplayed()
    }

    @Test
    fun topicList_showsNotStartedBadge() {
        val topics = makeTopics()
        val progress = CourseProgress(41, 22, 19, 53.7f)
        composeTestRule.setContent {
            LearnTrackTheme {
                TopicListScreen(
                    uiState = CourseUiState.Success(progress, topics),
                    onTopicClick = {},
                    onBack = {},
                    onRetry = {}
                )
            }
        }
        composeTestRule.onNodeWithText("Not Started").assertIsDisplayed()
    }

    @Test
    fun topicList_showsAtLeastTwelveTopicRows() {
        val topics = makeTopics()
        val progress = CourseProgress(41, 22, 19, 53.7f)
        composeTestRule.setContent {
            LearnTrackTheme {
                TopicListScreen(
                    uiState = CourseUiState.Success(progress, topics),
                    onTopicClick = {},
                    onBack = {},
                    onRetry = {}
                )
            }
        }
        // Topic 1 through 12 should be visible without scrolling
        composeTestRule.onNodeWithText("Topic 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Topic 12").assertIsDisplayed()
    }
}
