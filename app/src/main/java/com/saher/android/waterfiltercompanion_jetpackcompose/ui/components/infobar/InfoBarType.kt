package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.infobar

import androidx.compose.ui.graphics.Color
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorInfoBarErrorBg
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorInfoBarFg
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorInfoBarInfoBg
import com.saher.android.waterfiltercompanion_jetpackcompose.ui.theme.ColorInfoBarWarnBg


enum class InfoBarType(
    val bgColor: Color,
    val fgColor: Color
) {
    INFO(bgColor = ColorInfoBarInfoBg, fgColor = ColorInfoBarFg),
    WARN(bgColor = ColorInfoBarWarnBg, fgColor = ColorInfoBarFg),
    ERROR(bgColor = ColorInfoBarErrorBg, fgColor = ColorInfoBarFg)
}