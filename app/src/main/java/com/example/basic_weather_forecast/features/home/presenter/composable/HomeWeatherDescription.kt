package com.example.basic_weather_forecast.features.home.presenter.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.basic_weather_forecast.common.theme.Typography
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel

@Composable
fun HomeWeatherDescriptionContent(
    weather: HomeCurrentWeatherResponseModel?, isCelsius: Boolean
) {
    Row {
        Text(
            text = "${weather?.weather?.get(0)?.description}",
            style = Typography.displaySmall
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Text(text = "${
            weather?.main?.tempMin?.let { HomeTemperatureConverter(isCelsius, it) }
        } / ${weather?.main?.tempMax?.let { HomeTemperatureConverter(isCelsius, it) }}",
            style = Typography.displayMedium
        )
    }
}