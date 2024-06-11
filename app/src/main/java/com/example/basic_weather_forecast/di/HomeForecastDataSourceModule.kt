package com.example.basic_weather_forecast.di

import com.example.basic_weather_forecast.features.home.datasource.HomeForecastRepository
import com.example.basic_weather_forecast.features.home.datasource.network.HomeForecastApi
import com.example.basic_weather_forecast.features.home.datasource.network.HomeForecastDataSourceNetwork
import com.example.basic_weather_forecast.features.home.domain.GetCurrentWeatherUseCase
import com.example.basic_weather_forecast.features.home.domain.GetGeocodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object HomeForecastDataSourceModule {

    @Provides
    fun provideHomeForecastRepository(
        dataForecastDataSourceNetwork: HomeForecastDataSourceNetwork
    ) = HomeForecastRepository(dataForecastDataSourceNetwork)

    @Provides
    fun provideForecastCurrentWeatherUseCase(
        forecastRepository: HomeForecastRepository
    ) = GetCurrentWeatherUseCase(forecastRepository)

    @Provides
    fun provideGeocodingUseCase(
        forecastRepository: HomeForecastRepository
    ) = GetGeocodeUseCase(forecastRepository)

    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeForecastApi =
        retrofit.create(HomeForecastApi::class.java)

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create()).build()
}