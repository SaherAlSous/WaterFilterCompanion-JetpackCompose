package com.saher.android.waterfiltercompanion_jetpackcompose.ui.screen.main

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.capacityInputdialog.CapacityInputDialog
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.confirmationdialog.ConfirmationDialog
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
                IntrinsicSize.Min
            ),
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
                fill=viewModel.waterFill,
                daysInUse = viewModel.daysInUse) // added the default values.
                DetailsCard(
                    modifier =
                    Modifier
                        .padding(
                        top = 0.dp),
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
                    onCancel =viewModel::onCancel,
                    onSave = viewModel::onSave
                )
            }
            //Implementing Dialogs
            CapacityInputDialog(config = viewModel.capacityInputDialogConfig)
            ConfirmationDialog(config = viewModel.confirmationDialogConfig)
        }
    }
}
