package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.consumewaterfab

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.saher.android.waterfiltercompanion_jetpackcompose.R

//https://developer.android.com/jetpack/compose/state#restore-ui-state
//https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#ExtendedFloatingActionButton(kotlin.Function0,kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Function0,androidx.compose.foundation.interaction.MutableInteractionSource,androidx.compose.ui.graphics.Shape,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.material.FloatingActionButtonElevation)
@Composable
fun ConsumeWaterFab(
    modifier: Modifier = Modifier,
    isShown: Boolean,
    onConsume:() -> Unit
) {

    if (!isShown) return

    FloatingActionButton(
        modifier = modifier,
        onClick = onConsume
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_lbaseline_local_dring_24_foreground),
            contentDescription = stringResource(id = R.string.consume_water)
        )
    }
    
}