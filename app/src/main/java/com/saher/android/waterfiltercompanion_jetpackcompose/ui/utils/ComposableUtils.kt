package com.saher.android.waterfiltercompanion_jetpackcompose.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.saher.android.waterfiltercompanion_jetpackcompose.R

/**
 * we created this utility to ready to get [null] value.
 */
@Composable
fun quantityStringResource(
    @PluralsRes id: Int,
    quantity: Int,
): String{
    return LocalContext.current.resources.getQuantityString(//getting a plurals from string file
        id,
        quantity,
        quantity //for FormatArg
    )
}

@Composable
fun quantityStringResourceWithFallback(
    @PluralsRes id: Int,
    quantity: Int?,
):String{
    return if (quantity!= null){
        quantityStringResource(id = id, quantity = quantity )
    }else{
        stringResource(id = R.string.not_available)
    }
}

/**
 * in case the use didn't put anything in the values.
 * we want to display N/A
 */
@Composable
fun stringResourceWithFallback(
    @StringRes stringResFormat: Int,
    argument: Any?
): String {
    return if (argument != null)
        stringResource(stringResFormat, argument)
    else stringResource(R.string.not_available)
}

@Composable
fun stringwithFallback(string: String?):String {
    return string?: stringResource(R.string.not_available)
}
/**
 * we created this extension to debug the code in case there is any issue.
 * we can create an extension to be as a template for the design of the boxes below.
 */
 fun Modifier.debugBoarder(
    color: Color = Color.Red
) : Modifier = this.border(
width = 1.dp,
    color = color
)