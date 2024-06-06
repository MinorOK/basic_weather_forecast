package com.example.basic_weather_forecast.features.whole_day.domain

import com.example.basic_weather_forecast.features.home.datasource.HomeForecastRepository
import com.example.basic_weather_forecast.features.whole_day.datasource.WholeDayForecastRepository
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayForecastRequestModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.ForecastWholeDayWeatherResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWholeDayWeatherUseCase @Inject constructor(private val forecastRepository: WholeDayForecastRepository) {
    fun execute(request: WholeDayForecastRequestModel): Flow<ForecastWholeDayWeatherResponseModel> {
        return forecastRepository.getWholeDayWeather(request)
    }
}