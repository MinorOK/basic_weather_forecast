package com.example.basic_weather_forecast.features.whole_day.domain.model

import com.example.basic_weather_forecast.features.whole_day.datasource.model.ForecastWholeDayWeatherResponseModel

sealed class WholeDayForecastUiState {
    data class Success(val data: ForecastWholeDayWeatherResponseModel) : WholeDayForecastUiState()
    object Error : WholeDayForecastUiState()
    object Loading : WholeDayForecastUiState()
    object Nothing : WholeDayForecastUiState()
}