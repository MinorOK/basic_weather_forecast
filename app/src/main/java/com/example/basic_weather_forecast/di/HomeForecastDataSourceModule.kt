package com.example.basic_weather_forecast.di

import android.content.Context
import com.example.basic_weather_forecast.features.home.datasource.HomeForecastRepository
import com.example.basic_weather_forecast.features.home.datasource.network.HomeForecastApi
import com.example.basic_weather_forecast.features.home.datasource.network.HomeForecastDataSourceNetwork
import com.example.basic_weather_forecast.features.home.domain.GetCurrentWeatherUseCase
import com.example.basic_weather_forecast.features.whole_day.domain.GetWholeDayWeatherUseCase
import com.example.basic_weather_forecast.datastore.domain.GetCityNameUseCase
import com.example.basic_weather_forecast.datastore.domain.SetCityNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class HomeForecastDataSourceModule {

    @Provides
    fun provideHomeForecastRepository(
        dataForecastDataSourceNetwork: HomeForecastDataSourceNetwork
    ) = HomeForecastRepository(dataForecastDataSourceNetwork)

    @Provides
    fun provideForecastCurrentWeatherUseCase(
        forecastRepository: HomeForecastRepository
    ) = GetCurrentWeatherUseCase(forecastRepository)

    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeForecastApi =
        retrofit.create(HomeForecastApi::class.java)

    @Provides
    fun provideHomeRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create()).build()
}