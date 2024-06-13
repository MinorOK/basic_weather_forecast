package com.example.basic_weather_forecast.features.home.presenter.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.common.theme.secondaryColor
import com.example.basic_weather_forecast.common.ui.WeatherIcon
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toCelsius
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toFahrenheit
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toGMTPlus7
import com.example.basic_weather_forecast.features.whole_day.domain.model.WholeDayForecastUiState

@Composable
fun HomeWeatherHourlyContent(
    wholeDayWeatherUiState: WholeDayForecastUiState,
    navigateToWholeDayScreen: (String) -> Unit,
    cityName: String,
    isCelsius: Boolean
) {
    Surface(shape = RoundedCornerShape(16.dp),
        color = secondaryColor,
        tonalElevation = 4.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable {
                navigateToWholeDayScreen(cityName)
            }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            when (wholeDayWeatherUiState) {
                is WholeDayForecastUiState.Success -> {
                    val detailList = wholeDayWeatherUiState.data.list
                    LazyRow {
                        items(detailList) {
                            HomeHourlyWeather(
                                it.weather[0].icon ?: "",
                                it.main.temp ?: 0.0,
                                it.dt.toGMTPlus7(),
                                isCelsius
                            )
                        }
                    }
                }

                else -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(71.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeHourlyWeather(
    icon: String, temp: Double, time: String, isCelsius: Boolean
) {
    val myTemp = if (isCelsius) temp.toCelsius() else temp.toFahrenheit()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(end = 12.dp)
    ) {
        Text(
            "$time ${stringResource(R.string.time_unit)}", color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
            ),
        )
        WeatherIcon(icon = icon)
        Text(
            "${myTemp}${stringResource(R.string.weather_unit)}",
            color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}