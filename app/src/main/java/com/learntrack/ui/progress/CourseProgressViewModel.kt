package com.learntrack.ui.progress

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learntrack.BuildConfig
import com.learntrack.domain.usecase.GetCourseProgressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LearnTrack"

@HiltViewModel
class CourseProgressViewModel @Inject constructor(
    private val getCourseProgressUseCase: GetCourseProgressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CourseUiState>(CourseUiState.Loading)
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    init {
        loadCourseProgress()
    }

    fun loadCourseProgress() {
        viewModelScope.launch {
            _uiState.value = CourseUiState.Loading
            getCourseProgressUseCase().collect { result ->
                _uiState.value = result.fold(
                    onSuccess = { (progress, topics) ->
                        if (topics.isEmpty()) {
                            CourseUiState.Empty
                        } else {
                            CourseUiState.Success(progress, topics)
                        }
                    },
                    onFailure = { e ->
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, "Failed to load course data", e)
                        }
                        CourseUiState.Error(
                            e.message ?: "Failed to load course data. Please try again."
                        )
                    }
                )
            }
        }
    }
}
