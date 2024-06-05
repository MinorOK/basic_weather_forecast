package com.example.basic_weather_forecast.domain.model

import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherResponseModel

sealed class ForecastWholeDayWeatherUiState {
    data class Success(val data: ForecastWholeDayWeatherResponseModel) : ForecastWholeDayWeatherUiState()
    object Error : ForecastWholeDayWeatherUiState()
    object Loading : ForecastWholeDayWeatherUiState()
    object Nothing : ForecastWholeDayWeatherUiState()
}