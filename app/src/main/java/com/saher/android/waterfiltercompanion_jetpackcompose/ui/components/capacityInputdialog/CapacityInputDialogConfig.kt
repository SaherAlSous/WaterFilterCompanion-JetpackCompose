package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.capacityInputdialog

import androidx.annotation.StringRes

/**
 * this data class is the display and functionality for the
 * Capacity Dialog.
 * it will be an instance within the viewModel
 */
data class CapacityInputDialogConfig(
    @StringRes val titleStringRes: Int,
    val initialInput: String?,
    val onSubmit: (String) -> Unit,
    val onDismiss: () -> Unit
)
