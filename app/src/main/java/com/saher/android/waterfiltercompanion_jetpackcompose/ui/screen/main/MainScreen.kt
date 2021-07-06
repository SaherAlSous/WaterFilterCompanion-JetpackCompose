package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.ringindicator.Ring
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorRingBackground
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorRingForeground
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.WaterFilterCompanionJetpackComposeTheme

@Composable
fun MainScreen(viewModel: MainViewModel) {
    WaterFilterCompanionJetpackComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {

            Ring(
                bgColor = MaterialTheme.colors.ColorRingBackground,
                fgColor = MaterialTheme.colors.ColorRingForeground,
                fill = 0.7f
            )
        }
    }
}