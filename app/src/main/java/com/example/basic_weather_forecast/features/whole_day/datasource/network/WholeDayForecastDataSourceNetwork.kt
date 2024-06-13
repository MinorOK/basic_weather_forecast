package com.example.basic_weather_forecast.features.whole_day.datasource.network

import com.example.basic_weather_forecast.features.home.datasource.model.Result
import com.example.basic_weather_forecast.features.whole_day.datasource.WholeDayForecastDataSource
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayWeatherResponseModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayForecastRequestModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class WholeDayForecastDataSourceNetwork @Inject constructor(
    private val wholeDayForecastApi: WholeDayForecastApi,
    private val wholeDayForecastMapper: WholeDayForecastMapper,
) : WholeDayForecastDataSource {

    override fun getWholeDayWeather(request: WholeDayForecastRequestModel): Flow<WholeDayWeatherResponseModel> {
        return flow {
            val response =
                wholeDayForecastApi.requestWholeDayWeather(request.cityName, request.apiKey)
            when (val mappedResult = wholeDayForecastMapper.apply(response)) {
                is Result.Success -> emit(mappedResult.data)
                is Result.Error -> throw IOException(mappedResult.message)
            }
        }.flowOn(Dispatchers.IO)
    }
}