package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.capacityInputdialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.saher.android.waterfiltercompanion_jetpackcompose.R


//https://developer.android.com/jetpack/compose/text#enter-modify-text

@Composable
fun CapacityInputDialog(
    modifier: Modifier = Modifier,
    config: CapacityInputDialogConfig? // Dialog Data Class
) {
    if (config == null) return

    /**
     * saving the input value --> we added getting and setting in the import to remove the error.
     * To save the dialog value during configuration change/rotation
     * we changed the function [remember] to[rememberSaveable]
     */
    var input by rememberSaveable {
        mutableStateOf(config.initialInput?: "")
    }

AlertDialog(
    modifier= modifier,
    onDismissRequest =  config.onDismiss ,
//    title = { we disabled this field to remove the space it creates.
//       //The design overlapped so we have to fix it.
//   },
    text = {
        Column(  ) {
           CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
               Text(
                   modifier= Modifier
                       .fillMaxWidth(),
                   text = stringResource(config.titleStringRes),
                   style = MaterialTheme.typography.h4,
                   textAlign = TextAlign.Center,
               )
           }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                value = input,
                onValueChange = {
                    if (isInputValid(it)) input =it //checking the value before proceeding.
                                },
                singleLine = true,
                //since this field doesn't have TextAling, we copy the method from LocalTextStyle
                // -->LocalTextStyle.current.copy
                textStyle = MaterialTheme.typography.h4.copy(textAlign = TextAlign.Center),

                keyboardOptions = KeyboardOptions( //definging the content we want to insert in the field as numbers only.
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done //using the (tick) button on keyboard to submit the data
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onSubmit(input, config) }
                )
            )
        }
    },
    buttons = {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp
                )
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.End) {
            val buttonModifier = Modifier.align(Alignment.CenterVertically)
            TextButton(
                onClick =  config.onDismiss ,
                modifier = buttonModifier,
                colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colors.onSurface
                )
            ) {
                Text(
                    text = stringResource(R.string.capacity_input_dialog_cancel).uppercase()
                )
            }
            TextButton(
                onClick = { onSubmit(input, config) },
            modifier = buttonModifier) {
                Text(
                    text =  stringResource(R.string.capacity_input_dialog_submit).uppercase()
                )
            }
        }
    })
}

/**
 * this function is to check data entered by keyboard that it is not empty
 */
private fun onSubmit(input: String, config: CapacityInputDialogConfig){
    if (input.isNotEmpty()) config.onSubmit(input)
}

 /**
 * Rules:
 * - Max 3 digits
 * - Ignore any non-digit character
 * - Can be empty
 */
private fun isInputValid(input: String): Boolean{
    return input.isEmpty() ||
            (input.length <= 3 && input.matches(Regex("[0-9]*")))
}