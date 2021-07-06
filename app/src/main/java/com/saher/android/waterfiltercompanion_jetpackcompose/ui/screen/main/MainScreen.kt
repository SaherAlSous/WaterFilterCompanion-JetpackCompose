package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.WaterFilterCompanionJetpackComposeTheme

@Composable
fun MainScreen(viewModel: MainViewModel){
    WaterFilterCompanionJetpackComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            //Greeting("Android")
            Column {
                Text(text = "Current count: ${viewModel.counter}")
                Button(onClick = viewModel::increment) {
                    Text("Increment")
                }
            }
        }
    }
}