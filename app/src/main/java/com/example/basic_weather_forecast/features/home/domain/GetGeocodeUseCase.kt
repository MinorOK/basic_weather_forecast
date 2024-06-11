package com.example.basic_weather_forecast.features.home.domain

import com.example.basic_weather_forecast.features.home.datasource.HomeForecastRepository
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGeocodeUseCase @Inject constructor(private val forecastRepository: HomeForecastRepository) {
    fun execute(request: GeocodingRequestModel): Flow<List<GeocodingResponseModel>> {
        return forecastRepository.getGeocode(request)
    }
}