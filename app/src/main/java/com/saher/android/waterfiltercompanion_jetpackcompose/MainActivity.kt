package com.saher.android.waterfiltercompanion_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.WaterFilterCompanionJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterFilterCompanionJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //Greeting("Android")
                }
            }
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