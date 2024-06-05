package com.example.basic_weather_forecast.datasource

import com.example.basic_weather_forecast.datasource.model.ForecastCurrentWeatherRequestModel
import com.example.basic_weather_forecast.datasource.model.ForecastCurrentWeatherResponseModel
import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherRequestModel
import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastRepository @Inject constructor(private val forecastDataSource: ForecastDataSource) :
    ForecastDataSource by forecastDataSource {
    override fun getCurrentWeather(request: ForecastCurrentWeatherRequestModel): Flow<ForecastCurrentWeatherResponseModel> {
        return forecastDataSource.getCurrentWeather(request)
    }

    override fun getWholeDayWeather(request: ForecastWholeDayWeatherRequestModel): Flow<ForecastWholeDayWeatherResponseModel> {
        return forecastDataSource.getWholeDayWeather(request)
    }
}