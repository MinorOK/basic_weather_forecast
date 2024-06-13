package com.example.basic_weather_forecast.features.home.presenter.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel

@Composable
fun HomeWeatherGridBoxDetailSecondRowContent(
    weather: HomeCurrentWeatherResponseModel?, isCelsius: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        ) {
            HomeGridBoxDetail(
                painter = painterResource(id = R.drawable.ic_air_pressure),
                title = stringResource(R.string.air_pressure),
                value = weather?.main?.pressure.toString(),
                valueUnit = stringResource(R.string.air_pressure_unit)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        ) {
            HomeGridBoxDetail(
                painter = painterResource(id = R.drawable.ic_sun),
                title = stringResource(R.string.weather_feels_like),
                value = HomeTemperatureConverter(
                    isCelsius = isCelsius, temperature = weather?.main?.feelsLike ?: 0.0
                ),
                valueUnit = ""
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        ) {
            HomeGridBoxDetail(
                painter = painterResource(id = R.drawable.ic_visibility),
                title = stringResource(R.string.vision),
                value = (weather?.visibility?.div(1000)).toString(),
                valueUnit = stringResource(R.string.kilometer)
            )
        }
    }
}