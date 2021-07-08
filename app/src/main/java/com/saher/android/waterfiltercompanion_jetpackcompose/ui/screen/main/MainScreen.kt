package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.capacityInputdialog.CapacityInputDialog
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.confirmationdialog.ConfirmationDialog
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.consumewaterfab.ConsumeWaterFab
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.detailscard.DetailsCard
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.infobar.InfoBar
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.ringindicator.RingIndicator
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.WaterFilterCompanionJetpackComposeTheme

@ExperimentalAnimationApi
@Composable
fun MainScreen(viewModel: MainViewModel) {
    WaterFilterCompanionJetpackComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier =
            Modifier
                .fillMaxHeight(),
            color = MaterialTheme.colors.background
        ) {
            Box() {
                //extracting the modification into values to apply them on relative situation.
               val contentPaddingModifier = Modifier.padding(16.dp)
                //LandScape
//                val ringModifierLandScape= Modifier
//                    .padding(top = 16.dp)
//                    .fillMaxHeight()
//                    .width(300.dp)
                //val detailsCardModifierLandScape= Modifier.align(Alignment.CenterVertically)

                //Portrait
                val ringModifierPortrait= Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(300.dp)
                val detailsCardModifierPortrait= Modifier
                    .padding(top = 0.dp)

                //Getting an instance of the local configuration of screen, to let the UI interact with it.
                val configuration = LocalConfiguration.current
                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        Row(contentPaddingModifier) {

                            MainContent(
                                viewModel = viewModel,
                            detailsCardModifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically), //hardcoded because of compiler,
                            ringModifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxHeight()
                                .width(300.dp)// we defined the height of this section on screen
                                .weight(1f))
                        }
                    }
                    else -> {
                        Column(contentPaddingModifier) {
                            MainContent(
                                viewModel = viewModel,
                            detailsCardModifier = detailsCardModifierPortrait,
                            ringModifier = ringModifierPortrait)
                        }
                    }
                }

                //Implementing Dialogs
                CapacityInputDialog(config = viewModel.capacityInputDialogConfig)
                ConfirmationDialog(config = viewModel.confirmationDialogConfig)

                /**
                 * [InfoBar] Implementation
                 * Some settings for the info bar wont work because we are inside [Surface]
                 * therefore we will wrap the whole content inside a box.
                 */
                InfoBar(
                    offeredMessage = viewModel.infoBarMessage,
                    modifier = Modifier.align(Alignment.TopCenter),
                    onMessageTimeOut = viewModel::onInfoBarMessageTimeout
                )

                //Implementing Consume water Floating Button
                ConsumeWaterFab(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    isShown = viewModel.consumeFabVisible,
                    onConsume = viewModel::onConsume
                )
            }
        }
    }
}

/**
 * Preparing the app screen for rotation
 */
@ExperimentalAnimationApi
@Composable
fun MainContent(
    viewModel: MainViewModel,
    ringModifier: Modifier,
    detailsCardModifier: Modifier) {
    RingIndicator(
        ringModifier,
        fill = viewModel.waterFill,
        daysInUse = viewModel.daysInUse
    ) // added the default values.
    DetailsCard(
        modifier = detailsCardModifier,
        editMode = viewModel.editMode,
        //Total Capacity
        totalCapacity = viewModel.totalCapacity,
        onTotalCapacityClick = viewModel::onTotalCapacityClick,
        totalCapacityCandidate = viewModel.totalCapacityCandidate,

        //Remaining Capacity
        remainingCapacity = viewModel.remainingCapacity,
        onRemainingCapacityClick = viewModel::onRemainingCapacityClick,
        remainingCapacityCandidate = viewModel.remainingCapacityCandidate,

        //Installed on Formatted
        installOnFormatted = viewModel.installedOnFormatted,
        onInstallOnFormatted = viewModel::onInstalledOnClick,
        installedOnCandidateFormatted = viewModel.installedOnCandidateFormatted,

        onEdit = viewModel::onEdit,
        onClearData = viewModel::onClearData,
        onCancel = viewModel::onCancel,
        onSave = viewModel::onSave
    )

}
