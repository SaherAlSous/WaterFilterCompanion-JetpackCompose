package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.infobar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

const val SHOW_DELAY = 200L

@ExperimentalAnimationApi
@Composable
fun InfoBar(
    modifier: Modifier = Modifier,
    offeredMessage: InfoBarMessage?,
    // hiding the infobar using this call back from ViewModel
    onMessageTimeOut: () -> Unit
) {

    val displayedMessage: MutableState<InfoBarMessage?> = remember {
        mutableStateOf(null)
    }

    /**
     * We created this Boolean to allow Info Bar animation to exist,
     * because we are making its message value as null because the
     * exit animation start. (inside [showMessage])
     */
    val isShown : MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    offeredMessage?.let { messageOffered ->
        LaunchedEffect(messageOffered){
            showMessage(
                offeredMessage = offeredMessage,
                onMessageTimeOut = onMessageTimeOut,
                displayedMessage = displayedMessage,
                isShown = isShown
            )
        }
    }

    //Animating the info bar
    AnimatedVisibility(
        visible = isShown.value, //displayedMessage.value != null <-- we linked the visibility with the boolean
        enter = slideInVertically( //overriding the defaults.
            initialOffsetY = { fullHeight ->
                -fullHeight },
            animationSpec = tween(
                durationMillis = 150,
                easing = LinearOutSlowInEasing //gradually slower towards the end.
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight ->
                -fullHeight},
            animationSpec = tween(
                durationMillis = 250,
                easing = FastOutLinearInEasing //gradually faster towards the end.
            )
        )
    ) {
        displayedMessage.value?.let { message ->
            Surface (
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = 8.dp,
                color = message.type.bgColor
            ){
                Text(
                    modifier = modifier.padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    ) ,
                    text = stringResource(message.textStringRes),
                    color = message.type.fgColor,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }




}

//Hiding the InfoBar function
private suspend fun showMessage(
    offeredMessage: InfoBarMessage,
    displayedMessage: MutableState<InfoBarMessage?>,
    isShown: MutableState<Boolean>,
    onMessageTimeOut: () -> Unit
){
    //Each time we get a request for a message we remove the one before.
    //displayedMessage.value=null
    isShown.value =false
    //We made a delay for the next message to show up, this will make it decent.
    delay(SHOW_DELAY)
    displayedMessage.value = offeredMessage
    // only here the message is shown.after the delay, it will disappear
    isShown.value = true

    delay(TimeUnit.SECONDS.toMillis(offeredMessage.displayTimeSeconds))

    //displayedMessage.value= null
    isShown.value =false

    onMessageTimeOut()
}