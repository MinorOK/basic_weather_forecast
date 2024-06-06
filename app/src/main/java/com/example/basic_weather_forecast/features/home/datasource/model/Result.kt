package com.example.basic_weather_forecast.features.home.datasource.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}