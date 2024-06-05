package com.example.basic_weather_forecast.datasource.network

import com.example.basic_weather_forecast.datasource.ForecastDataSource
import com.example.basic_weather_forecast.datasource.model.ForecastCurrentWeatherRequestModel
import com.example.basic_weather_forecast.datasource.model.ForecastCurrentWeatherResponseModel
import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherRequestModel
import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherResponseModel
import com.example.basic_weather_forecast.datasource.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class ForecastDataSourceNetwork @Inject constructor(
    private val forecastApi: ForecastApi,
    private val forecastCurrentWeatherMapper: ForecastCurrentWeatherMapper,
    private val forecastWholeDayWeatherMapper: ForecastWholeDayWeatherMapper
) : ForecastDataSource {
    override fun getCurrentWeather(request: ForecastCurrentWeatherRequestModel): Flow<ForecastCurrentWeatherResponseModel> {
        return flow {
            val response = forecastApi.requestCurrentWeather(request.cityName, request.apiKey)
            when (val mappedResult = forecastCurrentWeatherMapper.apply(response)) {
                is Result.Success -> emit(mappedResult.data)
                is Result.Error -> throw IOException(mappedResult.message)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getWholeDayWeather(request: ForecastWholeDayWeatherRequestModel): Flow<ForecastWholeDayWeatherResponseModel> {
        return flow {
            val response = forecastApi.requestWholeDayWeather(request.cityName, request.apiKey)
            when (val mappedResult = forecastWholeDayWeatherMapper.apply(response)) {
                is Result.Success -> emit(mappedResult.data)
                is Result.Error -> throw IOException(mappedResult.message)
            }
        }.flowOn(Dispatchers.IO)
    }
}
