package com.example.basic_weather_forecast.datasource.network

import com.example.basic_weather_forecast.datasource.model.ForecastCurrentWeatherResponseModel
import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {
    @GET("weather")
    suspend fun requestCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Response<ForecastCurrentWeatherResponseModel>

    @GET("forecast")
    suspend fun requestWholeDayWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Response<ForecastWholeDayWeatherResponseModel>
}


