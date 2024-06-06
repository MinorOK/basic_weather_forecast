package com.example.basic_weather_forecast.features.home.datasource.network

import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeForecastApi {
    @GET("data/2.5/weather")
    suspend fun requestCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Response<HomeForecastResponseModel>

    @GET("geo/1.0/direct")
    suspend fun requestGeocoding(
        @Query("q") cityName: String,
        @Query("limit") responseLimit: Int,
        @Query("appid") apiKey: String
    ): Response<GeocodingResponseModel>
}
