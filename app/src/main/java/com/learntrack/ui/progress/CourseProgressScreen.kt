package com.learntrack.ui.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Stateless composable for the course progress dashboard.
 * All state is provided via [uiState]; no ViewModel access inside.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseProgressScreen(
    uiState: CourseUiState,
    onNavigateToTopics: () -> Unit,
    onRetry: () -> Unit
) {
    when (uiState) {
        is CourseUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.semantics {
                        contentDescription = "Loading course progress"
                    }
                )
            }
        }

        is CourseUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
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
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No topics found",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        is CourseUiState.Success -> {
            val progress = uiState.progress
            val percentText = "%.1f".format(progress.completionPercentage)

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("LearnTrack") }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "AI & Python Technology Course",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "$percentText%",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.semantics {
                            contentDescription = "Course completion: $percentText percent"
                        }
                    )

                    LinearProgressIndicator(
                        progress = { progress.completionPercentage / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .semantics {
                                contentDescription = "Course progress: $percentText percent"
                            }
                    )

                    Text(
                        text = "${progress.completed} of ${progress.total} topics completed",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onNavigateToTopics,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("View All Topics")
                    }
                }
            }
        }
    }
}

/**
 * Screen-level composable that collects state from the ViewModel.
 * This is the entry point used by the NavHost.
 */
@Composable
fun CourseProgressScreenRoute(
    viewModel: CourseProgressViewModel,
    onNavigateToTopics: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CourseProgressScreen(
        uiState = uiState,
        onNavigateToTopics = onNavigateToTopics,
        onRetry = { viewModel.loadCourseProgress() }
    )
}
