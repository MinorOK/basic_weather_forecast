package com.example.basic_weather_forecast.features.home.datasource

import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallResponseModel
import kotlinx.coroutines.flow.Flow

interface HomeForecastDataSource {
    fun getCurrentWeather(request: HomeCurrentWeatherRequestModel): Flow<HomeCurrentWeatherResponseModel>
    fun getGeocode(request: GeocodingRequestModel): Flow<List<GeocodingResponseModel>>
    fun getOneCall(request: HomeOneCallRequestModel): Flow<HomeOneCallResponseModel>
}