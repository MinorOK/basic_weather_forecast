package com.example.basic_weather_forecast.features.whole_day.domain

import com.example.basic_weather_forecast.features.home.datasource.ForecastRepository
import com.example.basic_weather_forecast.features.home.datasource.model.ForecastWholeDayWeatherRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.ForecastWholeDayWeatherResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWholeDayWeatherUseCase @Inject constructor(private val forecastRepository: ForecastRepository) {
    fun execute(request: ForecastWholeDayWeatherRequestModel): Flow<ForecastWholeDayWeatherResponseModel> {
        return forecastRepository.getWholeDayWeather(request)
    }
}