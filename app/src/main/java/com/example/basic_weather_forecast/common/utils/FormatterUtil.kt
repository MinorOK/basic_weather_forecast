package com.example.basic_weather_forecast.common.utils;

import com.example.basic_weather_forecast.features.whole_day.datasource.model.ListElement
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object FormatterUtil {
    // Kelvin to Celsius
    fun Double?.toCelsius(): Int? {
        return this?.minus(273.15)?.toInt()
    }

    // Kelvin to Fahrenheit
    fun Double?.toFahrenheit(): Int? {
        return this?.let { ((it - 273.15) * 9 / 5 + 32).toInt() }
    }

    fun Long?.toGMTPlus7(): String {
        return this?.let {
            val date = Date(it * 1000L)
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("GMT+7")
            format.format(date)
        } ?: ""
    }

    fun List<ListElement>.groupByDate(): Map<String, List<ListElement>> {
        return this.groupBy {
            it.dt.toDateString()
        }
    }

    private fun Long?.toDateString(): String {
        return this?.let {
            val date = Date(it * 1000L)
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("GMT+7")
            format.format(date)
        } ?: ""
    }

    fun Long?.toTimeString(): String {
        return this?.let {
            val date = Date(it * 1000L)
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("GMT+7")
            format.format(date)
        } ?: ""
    }
}