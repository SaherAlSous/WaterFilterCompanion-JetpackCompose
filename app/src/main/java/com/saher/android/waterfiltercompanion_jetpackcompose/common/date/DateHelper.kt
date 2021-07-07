package com.saher.android.waterfiltercompanion_jetpackcompose.common.date

import java.sql.Timestamp
import java.util.*
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class DateHelper {

    private val simpleDateFormat = SimpleDateFormat(
        PATTERN,
        Locale.US
    )

    /**
     * getting the date displayed on screen after inputting the date by millis.
     */
    fun getFormattedDate(timestamp: Long):String?= simpleDateFormat.format(Date(timestamp))

    /**
     * this function to count the difference between start date and current date
     * in Millis and convert them into days (int)
     */
    fun getDaysSince(timestamp: Long):Int?{
        val currentTime = System.currentTimeMillis()
        val diff = currentTime - timestamp
        return if (diff <= 0) {
            null
        } else {
            TimeUnit.MILLISECONDS.toDays(diff).toInt()
        }
    }

    companion object{
        const val PATTERN = "MMM d"
    }
}