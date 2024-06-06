package com.example.basic_weather_forecast.features.home.datasource.network

import com.example.basic_weather_forecast.features.home.datasource.HomeForecastDataSource
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastResponseModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayForecastRequestModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.ForecastWholeDayWeatherResponseModel
import com.example.basic_weather_forecast.common.model.Result
import com.example.basic_weather_forecast.features.whole_day.datasource.network.WholeDayForecastMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class HomeForecastDataSourceNetwork @Inject constructor(
    private val homeForecastApi: HomeForecastApi,
    private val homeForecastMapper: HomeForecastMapper,
) : HomeForecastDataSource {

    override fun getCurrentWeather(request: HomeForecastRequestModel): Flow<HomeForecastResponseModel> {
        return flow {
            val response = homeForecastApi.requestCurrentWeather(request.cityName, request.apiKey)
            when (val mappedResult = homeForecastMapper.apply(response)) {
                is Result.Success -> emit(mappedResult.data)
                is Result.Error -> throw IOException(mappedResult.message)
            }
        }.flowOn(Dispatchers.IO)
    }
}
