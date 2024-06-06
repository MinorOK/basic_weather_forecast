package com.example.basic_weather_forecast.features.home.domain.model

import com.example.basic_weather_forecast.features.home.datasource.model.ForecastCurrentWeatherResponseModel

sealed class ForecastCurrentWeatherUiState {

    data class Success(val data: ForecastCurrentWeatherResponseModel) : ForecastCurrentWeatherUiState()
    data class Error(val message: String) : ForecastCurrentWeatherUiState()
    object Loading : ForecastCurrentWeatherUiState()
    object Nothing : ForecastCurrentWeatherUiState()
}