package com.example.basic_weather_forecast.features.home.domain

import com.example.basic_weather_forecast.features.home.datasource.HomeForecastRepository
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val forecastRepository: HomeForecastRepository) {
    fun execute(request: HomeForecastRequestModel): Flow<HomeForecastResponseModel> {
        return forecastRepository.getCurrentWeather(request)
    }
}