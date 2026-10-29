package com.learntrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.learntrack.navigation.LearnTrackNavHost
import com.learntrack.ui.progress.CourseProgressViewModel
import com.learntrack.ui.theme.LearnTrackTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearnTrackTheme {
                val viewModel: CourseProgressViewModel = hiltViewModel()
                LearnTrackNavHost(viewModel = viewModel)
            }
        }
    }
}
