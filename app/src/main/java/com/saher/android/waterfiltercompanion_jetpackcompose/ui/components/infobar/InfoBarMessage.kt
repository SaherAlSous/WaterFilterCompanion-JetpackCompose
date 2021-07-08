package com.saher.android.waterfiltercompanion_jetpackcompose.ui.components.infobar

import androidx.annotation.StringRes

data class InfoBarMessage(
    val type: InfoBarType,
    @StringRes val textStringRes: Int,
    val args: List<Any>? = null, // allowing the message string to have arguments.
    val displayTimeSeconds: Long = 3L,
    /**
     * this will make every message into the Info Bar Unique and
     * therefore create a unique message call each time we ask for a
     * message to be displayed. otherwise if there is a message displayed on the
     * info bar, we can't call for other messages until it is timed-out.
     */
    val creationTime: Long = System.currentTimeMillis()
)
