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
fun HomeWeatherGridBoxDetailFirstRowContent(
    weather: HomeCurrentWeatherResponseModel?,
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
                painter = painterResource(id = R.drawable.ic_wind),
                title = stringResource(R.string.weather_wind_speed),
                value = weather?.wind?.speed.toString(),
                valueUnit = stringResource(R.string.kilo_per_hour)
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
                painter = painterResource(id = R.drawable.ic_humidity),
                title = stringResource(R.string.weather_humidity),
                value = weather?.main?.humidity.toString(),
                valueUnit = stringResource(R.string.percentage)
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