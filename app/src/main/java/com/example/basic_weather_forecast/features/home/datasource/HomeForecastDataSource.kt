package com.example.basic_weather_forecast.features.home.datasource

import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastResponseModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayForecastRequestModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.ForecastWholeDayWeatherResponseModel
import kotlinx.coroutines.flow.Flow

interface HomeForecastDataSource {
    fun getCurrentWeather(request: HomeForecastRequestModel): Flow<HomeForecastResponseModel>
    fun getGeocode(request: GeocodingRequestModel): Flow<List<GeocodingResponseModel>>
}