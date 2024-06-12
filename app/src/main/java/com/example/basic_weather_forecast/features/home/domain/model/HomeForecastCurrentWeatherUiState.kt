package com.example.basic_weather_forecast.features.home.domain.model

import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel

sealed class HomeForecastCurrentWeatherUiState {

    data class Success(val data: HomeCurrentWeatherResponseModel) : HomeForecastCurrentWeatherUiState()
    data class Error(val message: String) : HomeForecastCurrentWeatherUiState()
    object Loading : HomeForecastCurrentWeatherUiState()
    object Nothing : HomeForecastCurrentWeatherUiState()
}