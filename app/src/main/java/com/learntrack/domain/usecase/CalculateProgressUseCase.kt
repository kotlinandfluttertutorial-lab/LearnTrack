package com.learntrack.domain.usecase

import com.learntrack.data.model.Topic
import com.learntrack.data.model.TopicStatus
import com.learntrack.domain.model.CourseProgress
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * Calculates [CourseProgress] from a list of [Topic] objects.
 * Progress is always derived from data — never hard-coded.
 */
class CalculateProgressUseCase @Inject constructor() {

    operator fun invoke(topics: List<Topic>): CourseProgress {
        val total = topics.size
        val completed = topics.count { it.status == TopicStatus.COMPLETED }
        val notStarted = topics.count { it.status == TopicStatus.NOT_STARTED }
        val completionPercentage = if (total == 0) {
            0f
        } else {
            val raw = completed.toFloat() / total.toFloat() * 100f
            // Round to 1 decimal place
            (raw * 10f).roundToInt() / 10f
        }
        return CourseProgress(
            total = total,
            completed = completed,
            notStarted = notStarted,
            completionPercentage = completionPercentage
        )
    }
}
