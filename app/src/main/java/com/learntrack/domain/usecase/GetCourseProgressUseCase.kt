package com.learntrack.domain.usecase

import com.learntrack.data.model.Topic
import com.learntrack.data.repository.CourseRepository
import com.learntrack.domain.model.CourseProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case that combines topic fetching and progress calculation.
 * Returns a [Flow] of [Result] pairs: progress and full topic list.
 */
class GetCourseProgressUseCase @Inject constructor(
    private val repository: CourseRepository,
    private val calculateProgress: CalculateProgressUseCase
) {
    operator fun invoke(): Flow<Result<Pair<CourseProgress, List<Topic>>>> {
        return repository.getTopics().map { result ->
            result.map { topics ->
                val progress = calculateProgress(topics)
                Pair(progress, topics)
            }
        }
    }
}
