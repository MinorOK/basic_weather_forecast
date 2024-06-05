package com.example.basic_weather_forecast.domain

import com.example.basic_weather_forecast.datasource.ForecastRepository
import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherRequestModel
import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWholeDayWeatherUseCase @Inject constructor(private val forecastRepository: ForecastRepository) {
    fun execute(request: ForecastWholeDayWeatherRequestModel): Flow<ForecastWholeDayWeatherResponseModel> {
        return forecastRepository.getWholeDayWeather(request)
    }
}