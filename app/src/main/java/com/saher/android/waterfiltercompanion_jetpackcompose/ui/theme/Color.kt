package com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color
import androidx.core.content.res.ResourcesCompat

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val BlueE6 = Color(0xFF00CEE6)
val BlueFF = Color(0XFF00E5FF)

/**
 * We created a Ring indicator, therefore we can change color
 * of the ring based on the theme of the device/app, if it is
 * light or dark. we defind [ColorRingBackground] as an extension
 * to [Colors]
 */
val Colors.ColorRingBackground : Color
get() = if (isLight) Color.LightGray else Color.DarkGray

/**
*Creating the colors of the foreground ring, to be inserted from the
 * main function call
*/

val Colors.ColorRingForeground :Color
get() = if (isLight) BlueE6 else BlueFF