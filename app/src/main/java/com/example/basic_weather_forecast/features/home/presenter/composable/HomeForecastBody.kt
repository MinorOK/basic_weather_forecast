package com.example.basic_weather_forecast.features.home.presenter.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basic_weather_forecast.common.mock.ResponseMock
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel
import com.example.basic_weather_forecast.features.whole_day.presenter.WholeDayViewModel

@Composable
fun ForecastMainScreenBody(
    currentWeather: HomeCurrentWeatherResponseModel?,
    navigateToWholeDayScreen: (String) -> Unit,
    cityName: String,
    isCelsiusState: State<Boolean>,
    wholeDayViewModel: WholeDayViewModel = hiltViewModel()
) {
    val isCelsius by isCelsiusState
    val wholeDayWeatherUiState by wholeDayViewModel.wholeDayWeatherUiState.collectAsState()

    LaunchedEffect(wholeDayViewModel) {
        cityName.let {
            wholeDayViewModel.getWholeDayWeather(it)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        HomeCurrentTemperatureContent(isCelsius, currentWeather)
        Spacer(modifier = Modifier.height(4.dp))
        HomeWeatherDescriptionContent(currentWeather, isCelsius)
        Spacer(modifier = Modifier.height(40.dp))
        HomeWeatherHourlyContent(
            wholeDayWeatherUiState,
            navigateToWholeDayScreen,
            cityName,
            isCelsius
        )
        HomeWeatherGridBoxDetailFirstRowContent(currentWeather)
        HomeWeatherGridBoxDetailSecondRowContent(weather = currentWeather, isCelsius = isCelsius)
    }
}


@Preview
@Composable
fun ForecastMainCurrentWeatherComponentPreview() {
    ForecastMainScreenBody(
        ResponseMock.homeCurrentWeather,
        {},
        "Bangkok",
        mutableStateOf(true),
    )
}

