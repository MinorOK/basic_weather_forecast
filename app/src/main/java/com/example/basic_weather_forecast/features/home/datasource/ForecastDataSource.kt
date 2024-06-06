package com.example.basic_weather_forecast.features.home.datasource

import com.example.basic_weather_forecast.features.home.datasource.model.ForecastCurrentWeatherRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.ForecastCurrentWeatherResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.ForecastWholeDayWeatherRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.ForecastWholeDayWeatherResponseModel
import kotlinx.coroutines.flow.Flow

interface ForecastDataSource {
    fun getCurrentWeather(request: ForecastCurrentWeatherRequestModel): Flow<ForecastCurrentWeatherResponseModel>
    fun getWholeDayWeather(request: ForecastWholeDayWeatherRequestModel): Flow<ForecastWholeDayWeatherResponseModel>
}