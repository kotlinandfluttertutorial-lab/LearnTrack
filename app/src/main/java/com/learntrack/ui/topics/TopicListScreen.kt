package com.learntrack.ui.topics

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.learntrack.data.model.Topic
import com.learntrack.data.model.TopicStatus
import com.learntrack.ui.progress.CourseProgressViewModel
import com.learntrack.ui.progress.CourseUiState

/**
 * Stateless composable for the topic list screen.
 * All state is provided via [uiState]; no ViewModel access inside.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicListScreen(
    uiState: CourseUiState,
    onTopicClick: (String) -> Unit,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Topics") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (uiState) {
            is CourseUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.semantics {
                            contentDescription = "Loading topics"
                        }
                    )
                }
            }

            is CourseUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = uiState.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        OutlinedButton(onClick = onRetry) {
                            Text("Retry")
                        }
                    }
                }
            }

            is CourseUiState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No topics found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            is CourseUiState.Success -> {
                TopicList(
                    topics = uiState.topics,
                    onTopicClick = onTopicClick,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun TopicList(
    topics: List<Topic>,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(topics) { topic ->
            TopicRow(topic = topic, onTopicClick = onTopicClick)
            HorizontalDivider()
        }
    }
}

@Composable
private fun TopicRow(
    topic: Topic,
    onTopicClick: (String) -> Unit
) {
    val statusLabel = when (topic.status) {
        TopicStatus.COMPLETED -> "completed"
        TopicStatus.NOT_STARTED -> "not started"
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTopicClick(topic.id) }
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .semantics {
                contentDescription = "Topic: ${topic.name}, status: $statusLabel"
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = topic.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        )
        StatusBadge(status = topic.status)
    }
}

/**
 * Screen-level composable that collects state from the ViewModel.
 * This is the entry point used by the NavHost.
 */
@Composable
fun TopicListScreenRoute(
    viewModel: CourseProgressViewModel,
    onTopicClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TopicListScreen(
        uiState = uiState,
        onTopicClick = onTopicClick,
        onBack = onBack,
        onRetry = { viewModel.loadCourseProgress() }
    )
}
