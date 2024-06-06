package com.example.basic_weather_forecast.features.home.datasource

import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastResponseModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayForecastRequestModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.ForecastWholeDayWeatherResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeForecastRepository @Inject constructor(private val homeForecastDataSource: HomeForecastDataSource) :
    HomeForecastDataSource by homeForecastDataSource {
    override fun getCurrentWeather(request: HomeForecastRequestModel): Flow<HomeForecastResponseModel> {
        return homeForecastDataSource.getCurrentWeather(request)
    }
}