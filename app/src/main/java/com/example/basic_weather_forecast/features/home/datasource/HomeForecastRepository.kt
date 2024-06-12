package com.example.basic_weather_forecast.features.home.datasource

import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeForecastRepository @Inject constructor(
    private val homeForecastDataSource: HomeForecastDataSource
) :
    HomeForecastDataSource by homeForecastDataSource {
    override fun getCurrentWeather(request: HomeCurrentWeatherRequestModel): Flow<HomeCurrentWeatherResponseModel> {
        return homeForecastDataSource.getCurrentWeather(request)
    }

    override fun getGeocode(request: GeocodingRequestModel): Flow<List<GeocodingResponseModel>> {
        return homeForecastDataSource.getGeocode(request)
    }

    override fun getOneCall(request: HomeOneCallRequestModel): Flow<HomeOneCallResponseModel> {
        return homeForecastDataSource.getOneCall(request)
    }
}