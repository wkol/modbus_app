package com.example.modbus

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val API_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
val CHART_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
val NO_TIME_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun convertDateToFloat(date: String, firstDate: String): Float {
    val dateTimestamp =
        (API_DATE_FORMAT.parse(date) as Date).time - (NO_TIME_DATE_FORMAT.parse(firstDate) as Date).time
    return dateTimestamp.toFloat()
}

class AxisDateFormatter(private val startDate: String) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {

        val originalTimestamp: Long =
            (NO_TIME_DATE_FORMAT.parse(startDate) as Date).time + value.toLong()
        val dateNew = Date()
        return try {
            dateNew.time = originalTimestamp
            CHART_DATE_FORMAT.format(dateNew).toString()
        } catch (e: Exception) {
            "err"
        }
    }
}

data class ChartReading(val date: String, val value: Double)
