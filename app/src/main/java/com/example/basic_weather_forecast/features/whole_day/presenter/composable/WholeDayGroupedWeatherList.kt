package com.example.basic_weather_forecast.features.whole_day.presenter.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.basic_weather_forecast.common.utils.FormatterUtil.groupByDate
import com.example.basic_weather_forecast.features.whole_day.datasource.model.ListElement
import com.example.basic_weather_forecast.features.whole_day.presenter.HourlyForecastListItem

@Composable
fun WholeDayGroupedWeatherList(
    weatherList: List<ListElement>, isCelsius: Boolean
) {
    val groupedWeather = weatherList.groupByDate()

    LazyColumn {
        groupedWeather.forEach { (date, weatherForDate) ->
            item {
                WholeDayDateHeader(date)
            }
            items(weatherForDate) { weatherItem ->
                HourlyForecastListItem(
                    weatherList = weatherItem, isCelsius = isCelsius
                )
            }
        }
    }
}