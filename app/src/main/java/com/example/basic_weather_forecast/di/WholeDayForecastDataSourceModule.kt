package com.example.basic_weather_forecast.di

import com.example.basic_weather_forecast.features.whole_day.datasource.WholeDayForecastRepository
import com.example.basic_weather_forecast.features.whole_day.datasource.network.WholeDayForecastApi
import com.example.basic_weather_forecast.features.whole_day.datasource.network.WholeDayForecastDataSourceNetwork
import com.example.basic_weather_forecast.features.whole_day.domain.GetWholeDayWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object WholeDayForecastDataSourceModule {

    @Provides
    fun provideWholeDayForecastRepository(
        dataForecastDataSourceNetwork: WholeDayForecastDataSourceNetwork
    ) = WholeDayForecastRepository(dataForecastDataSourceNetwork)

    @Provides
    fun provideForecastWholeDayWeatherUseCase(
        forecastRepository: WholeDayForecastRepository
    ) = GetWholeDayWeatherUseCase(forecastRepository)

    @Provides
    fun provideWholeDayApi(retrofit: Retrofit): WholeDayForecastApi =
        retrofit.create(WholeDayForecastApi::class.java)

}