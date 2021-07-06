package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.ringindicator

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max


private val bgStrokeWithDp: Dp = 8.dp
private val fgStrokeWithDp: Dp = 12.dp
private const val TAG = "Ring"

/**
 * for more info: https://developer.android.com/jetpack/compose/graphics
 */

@Composable
fun Ring(
    modifier: Modifier = Modifier, //exposing the modifier, therefore we may access it from the function call.
    bgColor: Color,
    fgColor: Color,
    fill: Float) {

    /**
     * We want to convert the [dp] to pixels, therefore we have
     * to get the current local density and have it as a receiver fort this block.
     * To save the device resources, we noticed that each time the value of [fill] is changed,
     * both [Stroke]are being recreated, therefore we want to use [remember] method to save the
     * values and get them each time the value of [fill] is changed.
     */
    var bgStroke: Stroke
    var fgStroke: Stroke
    with(LocalDensity.current){
        bgStroke= remember {
            Log.d(TAG,"bulding background stroke")
            Stroke(width = bgStrokeWithDp.toPx())
        }
        fgStroke= remember {
            Log.d(TAG, "builing foreground stroke")
            Stroke(
                fgStrokeWithDp.toPx(),
                cap = StrokeCap.Round)
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
    val fgRingAngleEdge = remember(fill) {
        180.0f * fill
    }

    /**
     * We want to be sure that both Rings have the same line/[Stroke] size.
     * we want to know which stroke is larger.
     * therefore instead of measuring the size of each [Stroke] for each one
     * separately, we use the maxsize between both inside [innerRadius]
     */
    val maxStroke = remember(fill) {
        max(bgStroke.width, fgStroke.width)
    }



    Canvas(
        modifier
            .fillMaxWidth()
            .height(300.dp)
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
            startAngle = 0.0f,
            endAngle = 360.0f,
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