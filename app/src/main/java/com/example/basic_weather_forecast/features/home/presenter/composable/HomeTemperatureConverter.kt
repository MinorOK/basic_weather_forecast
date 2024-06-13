package com.example.basic_weather_forecast.features.home.presenter.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toCelsius
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toFahrenheit

@Composable
fun HomeTemperatureConverter(
    isCelsius: Boolean, temperature: Double
): String {
    return temperature.let {
        if (isCelsius) "${it.toCelsius()}${stringResource(R.string.weather_unit)}"
        else "${it.toFahrenheit()}${stringResource(R.string.weather_unit)}"
    }
}