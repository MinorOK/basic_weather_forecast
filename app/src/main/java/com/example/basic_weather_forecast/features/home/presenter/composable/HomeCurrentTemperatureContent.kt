package com.example.basic_weather_forecast.features.home.presenter.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.basic_weather_forecast.common.theme.Typography
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel

@Composable
fun HomeCurrentTemperatureContent(
    isCelsius: Boolean, weather: HomeCurrentWeatherResponseModel?
) {
    Text(
        text = HomeTemperatureConverter(
            isCelsius = isCelsius,
            temperature = weather?.main?.temp ?: 0.0,
        ), style = Typography.displayLarge
    )
}