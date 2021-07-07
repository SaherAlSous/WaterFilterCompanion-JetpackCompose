package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.detailscard.content.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

/**
 * since we are creating the same column three times. we did this function
 * so we wont duplicate the code, and it is better for debugging
 */
@Composable
fun DetailsContentItem(
    modifier: Modifier = Modifier,
    value: String,
    label: String
){
    Column(modifier) {
        val itemModifier = Modifier
            .align(
                Alignment.CenterHorizontally
            )
        DetailsContentTextItemValue(
            value = value,
            modifier = itemModifier
        )
        DetailsContentItemLabel(
            label = label,
            modifier = itemModifier
        )
    }
}

@Composable
private fun DetailsContentTextItemValue(
    modifier: Modifier = Modifier,
    value : String
){
    Text(
        text = value,
        modifier = modifier,
        style = MaterialTheme.typography.h4
    )
}

@Composable
private fun DetailsContentItemLabel(
    modifier: Modifier = Modifier,
    label : String
){
    Text(
        text = label.uppercase(),
        modifier = modifier,
        style = MaterialTheme.typography.overline,
        fontSize = 12.sp,
    )
}