package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.WaterFilterCompanionJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
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