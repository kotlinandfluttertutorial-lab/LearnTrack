package com.learntrack.ui.topics

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.learntrack.data.model.TopicStatus
import com.learntrack.ui.theme.CompletedContainer
import com.learntrack.ui.theme.CompletedGreen
import com.learntrack.ui.theme.WarningContainer
import com.learntrack.ui.theme.WarningText

/**
 * A chip that displays a topic's completion status.
 * Uses green tonal styling for COMPLETED and amber for NOT_STARTED.
 */
@Composable
fun StatusBadge(
    status: TopicStatus,
    modifier: Modifier = Modifier
) {
    val (label, containerColor, labelColor) = when (status) {
        TopicStatus.COMPLETED -> Triple("Completed", CompletedContainer, CompletedGreen)
        TopicStatus.NOT_STARTED -> Triple("Not Started", WarningContainer, WarningText)
    }

    FilterChip(
        selected = false,
        onClick = {},
        label = { Text(text = label, style = MaterialTheme.typography.labelMedium) },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = containerColor,
            labelColor = labelColor
        ),
        modifier = modifier.semantics {
            contentDescription = "Status: ${status.name.lowercase().replace('_', ' ')}"
        }
    )
}
