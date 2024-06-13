package com.example.basic_weather_forecast.features.home.datasource.network

import com.example.basic_weather_forecast.features.home.datasource.HomeForecastDataSource
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.Result
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class HomeForecastDataSourceNetwork @Inject constructor(
    private val homeForecastApi: HomeForecastApi,
    private val homeForecastMapper: HomeForecastMapper,
    private val homeGeocodeMapper: HomeGeocodeMapper,
    private val homeOneCallMapper: HomeOneCallMapper
) : HomeForecastDataSource {

    override fun getCurrentWeather(request: HomeCurrentWeatherRequestModel): Flow<HomeCurrentWeatherResponseModel> {
        return flow {
            val response = homeForecastApi.requestCurrentWeather(request.cityName, request.apiKey)
            when (val mappedResult = homeForecastMapper.apply(response)) {
                is Result.Success -> emit(mappedResult.data)
                is Result.Error -> throw IOException(mappedResult.message)
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getGeocode(request: GeocodingRequestModel): Flow<List<GeocodingResponseModel>> {
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

    override fun getOneCall(request: HomeOneCallRequestModel): Flow<HomeOneCallResponseModel> {
        return flow {
            val response = homeForecastApi.requestOneCall(
                request.lat,
                request.long,
                request.apiKey
            )
            when (val mappedResult = homeOneCallMapper.apply(response)) {
                is Result.Success -> emit(mappedResult.data)
                is Result.Error -> throw IOException(mappedResult.message)
            }
        }
    }

}
