package com.jonnyhsia.storybook.helper

import com.jonnyhsia.storybook.app.AppException
import java.util.Calendar

/**
 * Created by JonnyHsia on 17/11/3.
 */
object TimeKits {

    fun getStandardDate(): String {
        val cal = Calendar.getInstance()
        val weekday = cal.get(Calendar.DAY_OF_WEEK)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        return "${convert2WeekDayString(weekday)}, ${convert2MonthString(month)} $day"
    }

    /**
     * 把数字星期转换成字符串
     */
    private fun convert2WeekDayString(dayOfWeek: Int): String {
        return when (dayOfWeek - 1) {
            1 -> "Monday"
            2 -> "Tuesday"
            3 -> "Wednesday"
            4 -> "Thursday"
            5 -> "Friday"
            6 -> "Saturday"
            0 -> "Sunday"
            else -> throw AppException("Day of week is invalid!")
        }
    }

    /**
     * 把数字月份转换成字符串
     */
    private fun convert2MonthString(month: Int): String {
        return when (month + 1) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> throw AppException("Month is invalid!")
        }
    }
}