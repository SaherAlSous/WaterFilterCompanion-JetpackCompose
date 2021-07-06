package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.ringindicator.Ring
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.ringindicator.RingIndicator
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorRingBackground
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorRingForeground
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.WaterFilterCompanionJetpackComposeTheme

@Composable
fun MainScreen(viewModel: MainViewModel) {
    WaterFilterCompanionJetpackComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            RingIndicator(
                Modifier
                .fillMaxWidth()
                .height(300.dp), // we defined the height of this section on screen.
                fill=0.9f,
                daysInUse = null) // added the default values.
        }
    }
}