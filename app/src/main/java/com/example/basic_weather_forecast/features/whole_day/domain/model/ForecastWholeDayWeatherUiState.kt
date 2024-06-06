package com.example.basic_weather_forecast.features.whole_day.domain.model

import com.example.basic_weather_forecast.features.home.datasource.model.ForecastWholeDayWeatherResponseModel

sealed class ForecastWholeDayWeatherUiState {
    data class Success(val data: ForecastWholeDayWeatherResponseModel) : ForecastWholeDayWeatherUiState()
    object Error : ForecastWholeDayWeatherUiState()
    object Loading : ForecastWholeDayWeatherUiState()
    object Nothing : ForecastWholeDayWeatherUiState()
}