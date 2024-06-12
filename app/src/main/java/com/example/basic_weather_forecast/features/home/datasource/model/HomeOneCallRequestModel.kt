package com.example.basic_weather_forecast.features.home.datasource.model

import retrofit2.http.Query

data class HomeOneCallRequestModel(
    @Query("lat") val lat: Double,
    @Query("lon") val long: Double,
    @Query("appid") val apiKey: String = "5966d26e22e0a37b27f4186cd9df1a4b"
)