package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.detailscard.DetailsCard
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.ringindicator.RingIndicator
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.WaterFilterCompanionJetpackComposeTheme

@ExperimentalAnimationApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    WaterFilterCompanionJetpackComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier =
        Modifier
            .fillMaxWidth()
            .height(
                IntrinsicSize.Min),
            color = MaterialTheme.colors.background
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .wrapContentHeight()
            ) {
            RingIndicator(
                Modifier
                    .fillMaxWidth()
                    .height(300.dp), // we defined the height of this section on screen.
                fill=0.9f,
                daysInUse = null) // added the default values.
                DetailsCard(
                    modifier =
                    Modifier
                        .padding(
                        top = 0.dp),
                    editMode = true,
                    totalCapacity = null,
                    remainingCapacity = null,
                    installOnFormatted = null,
                    onEdit = { log("onEdit")},
                    onClearData = { log("onClearData")},
                    onCancel ={ log("onCancel")},
                    onSave = {log("onSave")}
                )
            }
        }
    }
}
private fun log(string: String){
    Log.d("MainScreen", string)
}