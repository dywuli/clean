package com.android.saleplatform.mapper

import java.text.SimpleDateFormat
import java.util.*

 object Converters {

    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    /**
     * 时间戳转日期
     */
    fun fromTimestamp(value: String?): Date {
        if (value == null) {
            return Date(System.currentTimeMillis())
        }
        synchronized(format) {
            return format.parse(value)
        }
    }

    /**
     * 日期转时间
     */
    fun dateToTimestamp(date: Date?): String {
        if (date == null) {
            val nowDate = Date(System.currentTimeMillis())
            synchronized(format) {
                return format.format(nowDate)
            }
        }
        synchronized(format) {
            return format.format(date)
        }
    }
}