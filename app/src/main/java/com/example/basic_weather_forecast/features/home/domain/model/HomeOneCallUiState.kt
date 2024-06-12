package com.example.basic_weather_forecast.features.home.domain.model

import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallResponseModel

sealed class HomeOneCallUiState {
    data class Success(val data: HomeOneCallResponseModel) : HomeOneCallUiState()
    data class Error(val message: String) : HomeOneCallUiState()
    object Loading : HomeOneCallUiState()
}