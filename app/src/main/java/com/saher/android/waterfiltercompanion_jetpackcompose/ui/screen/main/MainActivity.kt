package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.lifecycleScope
import com.saher.android.waterfiltercompanion_jetpackcompose.common.dialog.MaterialDialogHelper
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.WaterFilterCompanionJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var materialDialogHelper: MaterialDialogHelper //video 13

    //taking the date picker outside oncreate
    private val eventFlow by lazy {
        //Receiving the date picked
        viewModel.eventsFlow.onEach { event ->
            when (event) {
                is MainViewModel.Event.ShowDatePicker ->
                    materialDialogHelper.requestDateAsTimeStamp(
                        currentDateTimeStamp = event.currentDateTimeStamp,
                        cb = event.cb
                    )
            }
        }
    }

    //creating a job to cancel the coroutines of time picker
    private var eventJob: Job? = null

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }

    }

    override fun onStart() {
        super.onStart()
        eventJob = eventFlow.launchIn(lifecycleScope)
    }

    override fun onStop() {
        super.onStop()
        eventJob?.cancel()
        eventJob = null
    }
}

/**
@Composable
fun Greeting(name: String) {
/**
 * all the material design here are part of the material design language
 *
*/
Text(
text = "Hello $name!",
style = MaterialTheme.typography.h1
)
}

/**
 * this function, that has [Preview] annotation. calls whichever component you use
 * in your hierarchy, it is able to display it in the split panel
 * or design panel.
*/
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
WaterFilterCompanionJetpackComposeTheme {
Greeting("Android")
}
}
 */