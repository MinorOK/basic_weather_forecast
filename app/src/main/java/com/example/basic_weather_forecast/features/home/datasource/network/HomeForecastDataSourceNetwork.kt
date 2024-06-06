package com.example.basic_weather_forecast.features.home.datasource.network

import com.example.basic_weather_forecast.features.home.datasource.HomeForecastDataSource
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastResponseModel
import com.example.basic_weather_forecast.common.model.Result
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class HomeForecastDataSourceNetwork @Inject constructor(
    private val homeForecastApi: HomeForecastApi,
    private val homeForecastMapper: HomeForecastMapper,
    private val homeGeocodeMapper: HomeGeocodeMapper
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

    override fun getGeocode(request: GeocodingRequestModel): Flow<GeocodingResponseModel> {
        return flow {
            val response = homeForecastApi.requestGeocoding(
                request.cityName,
                request.limit,
                request.apiKey
            )
            when (val mappedResult = homeGeocodeMapper.apply(response)) {
                is Result.Success -> emit(mappedResult.data)
                is Result.Error -> throw IOException(mappedResult.message)
            }
        }.flowOn(Dispatchers.IO)
    }
}
