package com.example.basic_weather_forecast.features.whole_day.datasource

import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayWeatherResponseModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayForecastRequestModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WholeDayForecastRepository @Inject constructor(private val wholeDayForecastDataSource: WholeDayForecastDataSource) :
    WholeDayForecastDataSource by wholeDayForecastDataSource {
    override fun getWholeDayWeather(request: WholeDayForecastRequestModel): Flow<WholeDayWeatherResponseModel> {
        return wholeDayForecastDataSource.getWholeDayWeather(request)
    }
}