package com.example.basic_weather_forecast.features.home.domain.model

import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel

sealed class HomeGeocodeUiState {
    data class Success(val data: GeocodingResponseModel) : HomeGeocodeUiState()
    data class Error(val message: String) : HomeGeocodeUiState()
    object Loading : HomeGeocodeUiState()
}