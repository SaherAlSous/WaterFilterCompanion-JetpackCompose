package com.saher.android.waterfiltercompanion_jetpackcompose.common.dialog

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import java.util.*

class MaterialDialogHelper (
    private val context: Context
        ){

    fun requestDateAsTimeStamp(currentDateTimeStamp: Long?,cb: (Long) -> Unit){
        val currentDateCalendar = currentDateTimeStamp?.let {
            Calendar.getInstance().apply {
                timeInMillis = currentDateTimeStamp
            }
        }
        MaterialDialog(context).show {
            //MaxDate is to prevent the user from picking a date from the future
            datePicker(currentDate = currentDateCalendar, maxDate = Calendar.getInstance()) { dialog, datetime ->
                cb(datetime.timeInMillis)
            }

        }
    }
}