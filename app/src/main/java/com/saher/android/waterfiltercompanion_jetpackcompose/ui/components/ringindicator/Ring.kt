package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.ringindicator

import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max


private val bgStrokeWithDp: Dp = 8.dp
private val fgStrokeWithDp: Dp = 12.dp
private const val TAG = "Ring"

/**
 * for more info about graphics: https://developer.android.com/jetpack/compose/graphics
 * for more info about animation: https://developer.android.com/jetpack/compose/animation
 */

@Composable
fun Ring(
    modifier: Modifier = Modifier, //exposing the modifier, therefore we may access it from the function call.
    bgColor: Color,
    fgColor: Color,
    fill: Float,
    /**
     * In order to synchronize the foreground ring animation with the percentage text
     * displayed in the [RingIndicator] which is the parent of the [Ring] itself, we want to expose a way -> in which
     * the ring can pass a value to the outside. so for that we want to use a Callback.
     */
    fgFillCb: ((Float) -> Unit)? = null
) {

    /**
     * We want to convert the [dp] to pixels, therefore we have
     * to get the current local density and have it as a receiver fort this block.
     * To save the device resources, we noticed that each time the value of [fill] is changed,
     * both [Stroke]are being recreated, therefore we want to use [remember] method to save the
     * values and get them each time the value of [fill] is changed.
     */
    var bgStroke: Stroke
    var fgStroke: Stroke
    with(LocalDensity.current) {
        bgStroke = remember {
            Log.d(TAG, "bulding background stroke")
            Stroke(width = bgStrokeWithDp.toPx())
        }
        fgStroke = remember {
            Log.d(TAG, "builing foreground stroke")
            Stroke(
                fgStrokeWithDp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }


//    val bgStroke = with(LocalDensity.current) {
//        Log.d(TAG,"Building background Ring")
//        Stroke(
//            width = bgStrokeWithDp.toPx(),
//        )
//    }
//    val fgStroke = with(LocalDensity.current) {
//        Log.d(TAG,"Building foreground Ring")
//        Stroke(
//            width = fgStrokeWithDp.toPx(),
//            cap = StrokeCap.Round //making it with a round ends
//        )
//    }

    /**
     * We want to define the size of the foreground ring [fgRingAngleEdge] and mirror it on both size by
     * taking the start point as 180 degrees, and multiplied by the percent of the
     * current capacity.
     * to make this value dynamic, we want to input the ration from the [Ring] call.
     * Using [fill] parameter, will allow the app to recalculate the value and display
     * the new one, instead of sending back the same old value.
     */
//    val fgRingAngleEdge = remember(fill) { // we changed this value so that we can create another one for animation
//        180.0f * fill
//    }


    /**
     * We want to be sure that both Rings have the same line/[Stroke] size.
     * we want to know which stroke is larger.
     * therefore instead of measuring the size of each [Stroke] for each one
     * separately, we use the maxsize between both inside [innerRadius]
     */
    val maxStroke = remember(fill) {
        max(bgStroke.width, fgStroke.width)
    }

    /**
     * We are creating a [transitionState] value to apply animations, using [TransitionState] enum class
     */
    val transitionState = remember {
        MutableTransitionState(TransitionState.INIT_START)
    }

    /**
     * We will create the transition itself by using [updateTransition] to call
     * the [transitionState] - video 7
     */
    val transition = updateTransition(
        transitionState = transitionState,
        label = "ring-anim-transition"
    )

    /**
     * To start the animation, we need to delegate it to [transition] [animateFloat] - video 7
     */
    val bgRingAngelEdge by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 500,
                delayMillis = 500
            )
        },
        label = "bgRingAngelEdge"
    ) { currentState ->
        if (currentState == TransitionState.INIT_START) 0f else 180f
    }

    val fgRingAngleEdge by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 500
            )
        },
        label = "fgRingAngleEdge"
    ) { currentState ->
        if (currentState == TransitionState.FILLED) 180f * fill else 0f
    }

    /**
     * Creating an animation for the stats text on start as the with the ring
     */

    val fgFill by transition.animateFloat (
        transitionSpec = {
            tween(400)
        },
        label = "fgFill"
            ){currentState ->
        if (currentState == TransitionState.FILLED) fill else 0f
    }
    /**
     * We will use this [LaunchedEffect] to send the value of the [fgFillCb]
     */
    LaunchedEffect(fgFill){
        fgFillCb?.invoke(fgFill)
    }


    /**
     * to start the animation we have to use [LaunchedEffect] method
     */
    LaunchedEffect(key1 = transitionState.currentState) {
        transitionState.targetState = when (transitionState.currentState) {
            TransitionState.INIT_START -> TransitionState.INIT_END
            else -> TransitionState.FILLED
        }
    }

    Canvas(
        modifier
//            .border(
//                width = 1.dp,
//                color = Color.Red
//            )
    ) {
        /**
         * to center the circle in the middle, we need to find the center point x,y
         * we are doing that by calculating the [innerRadius] and [topLeft] point for the circle
         */
        val innerRadius = (size.minDimension - maxStroke) / 2
        val halfSize = size / 2f
        val topLeft = Offset(
            x = halfSize.width - innerRadius,
            y = halfSize.height - innerRadius

        )
        val arcSize = Size(
            width = innerRadius * 2,
            height = innerRadius * 2
        )
        /*
         drawArc(
             color = bgColor,
             startAngle = 0.0f,
             sweepAngle = 360.0f,
             useCenter = false,
             style = bgStroke,
             size= arcSize,
         topLeft = topLeft,
         )
         --> we did this way with helper method as an extension to
         [DrawScope] to ease the way we draw a circle. to put the
         default value for us automatically.

         */
        drawRing(
            //Background Rind
            color = bgColor,
            startAngle = -bgRingAngelEdge, // we changed the values here to apply the animation on the stroke
            endAngle = bgRingAngelEdge,
            style = bgStroke,
            size = arcSize,
            topLeft = topLeft,
        )

        drawRing( //ForegroundRing --> video 06
            color = fgColor,
            startAngle = 180 - fgRingAngleEdge,
            endAngle = 180 + fgRingAngleEdge,
            topLeft = topLeft, // we want the rings to be above each other.
            size = arcSize,
            style = fgStroke
        )
    }

}

/**
 * creating a helper method to allow us to draw another [Canvas]
 * on top of each other.
 */
private fun DrawScope.drawRing(
    color: Color,
    startAngle: Float,
    endAngle: Float,
    topLeft: Offset,
    size: Size,
    style: DrawStyle
) {
    drawArc(
        color = color,
        startAngle = startAngle - 90f,
        sweepAngle = endAngle - startAngle,
        useCenter = false,
        topLeft = topLeft,
        size = size,
        style = style
    )
}

/**
 * this to allow me to see a preview in the split screen
 * but the alpha version of the library is not working well.
 */
@Preview(showBackground = true)
@Composable
fun previewRing() {
    Ring(
        modifier = Modifier.size(300.dp),
        fill =0.8f,
        bgColor =Color.DarkGray,
        fgColor = Color.Cyan)
}