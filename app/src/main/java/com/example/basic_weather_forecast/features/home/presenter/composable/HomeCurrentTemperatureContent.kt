package com.example.basic_weather_forecast.features.home.presenter.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel

@Composable
fun HomeCurrentTemperatureContent(
    isCelsius: Boolean, weather: HomeCurrentWeatherResponseModel?
) {
    Text(
        text = HomeTemperatureConverter(
            isCelsius = isCelsius,
            temperature = weather?.main?.temp ?: 0.0,
        ), style = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 96.sp,
        )
    )
}