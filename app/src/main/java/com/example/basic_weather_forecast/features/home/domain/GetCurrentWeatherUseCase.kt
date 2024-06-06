package com.example.basic_weather_forecast.features.home.domain

import com.example.basic_weather_forecast.features.home.datasource.ForecastRepository
import com.example.basic_weather_forecast.features.home.datasource.model.ForecastCurrentWeatherRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.ForecastCurrentWeatherResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val forecastRepository: ForecastRepository) {
    fun execute(request: ForecastCurrentWeatherRequestModel): Flow<ForecastCurrentWeatherResponseModel> {
        return forecastRepository.getCurrentWeather(request)
    }
}