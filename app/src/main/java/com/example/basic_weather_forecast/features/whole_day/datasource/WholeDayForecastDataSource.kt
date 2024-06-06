package com.example.basic_weather_forecast.features.whole_day.datasource

import com.example.basic_weather_forecast.features.whole_day.datasource.model.ForecastWholeDayWeatherResponseModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayForecastRequestModel
import kotlinx.coroutines.flow.Flow

interface WholeDayForecastDataSource {
    fun getWholeDayWeather(request: WholeDayForecastRequestModel): Flow<ForecastWholeDayWeatherResponseModel>
}