package com.example.basic_weather_forecast.features.whole_day.domain.model

import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayWeatherResponseModel

sealed class WholeDayForecastUiState {
    data class Success(val data: WholeDayWeatherResponseModel) : WholeDayForecastUiState()
    object Error : WholeDayForecastUiState()
    object Loading : WholeDayForecastUiState()
    object Nothing : WholeDayForecastUiState()
}