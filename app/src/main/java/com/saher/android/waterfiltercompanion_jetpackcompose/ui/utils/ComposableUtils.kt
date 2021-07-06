package com.saher.android.waterfiltercompanion_jetpackcompose.ui.utils

import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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