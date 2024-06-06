package com.example.basic_weather_forecast.features.home.domain.model

import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastResponseModel

sealed class HomeForecastUiState {

    data class Success(val data: HomeForecastResponseModel) : HomeForecastUiState()
    data class Error(val message: String) : HomeForecastUiState()
    object Loading : HomeForecastUiState()
    object Nothing : HomeForecastUiState()
}