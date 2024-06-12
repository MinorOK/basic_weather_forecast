package com.example.basic_weather_forecast.features.home.datasource.network

import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeForecastApi {
    @GET("data/2.5/weather")
    suspend fun requestCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Response<HomeCurrentWeatherResponseModel>

    @GET("geo/1.0/direct")
    suspend fun requestGeocoding(
        @Query("q") cityName: String,
        @Query("limit") limit: String,
        @Query("appid") apiKey: String
    ): Response<List<GeocodingResponseModel>>

    @GET("data/3.0/onecall")
    suspend fun requestOneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): Response<HomeOneCallResponseModel>
}
