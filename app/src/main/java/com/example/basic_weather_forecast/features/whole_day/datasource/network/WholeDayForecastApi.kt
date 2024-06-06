package com.example.basic_weather_forecast.features.whole_day.datasource.network

import com.example.basic_weather_forecast.features.whole_day.datasource.model.ForecastWholeDayWeatherResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WholeDayForecastApi {
    @GET("forecast")
    suspend fun requestWholeDayWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Response<ForecastWholeDayWeatherResponseModel>
}