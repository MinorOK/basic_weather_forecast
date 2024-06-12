package com.example.basic_weather_forecast.features.home.domain

import com.example.basic_weather_forecast.features.home.datasource.HomeForecastRepository
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOneCallUseCase @Inject constructor(private val forecastRepository: HomeForecastRepository) {
    fun execute(request: HomeOneCallRequestModel): Flow<HomeOneCallResponseModel> {
        return forecastRepository.getOneCall(request)
    }
}