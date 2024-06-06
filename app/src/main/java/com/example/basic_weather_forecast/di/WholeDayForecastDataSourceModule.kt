package com.example.basic_weather_forecast.di

import com.example.basic_weather_forecast.features.home.datasource.HomeForecastRepository
import com.example.basic_weather_forecast.features.home.datasource.network.HomeForecastApi
import com.example.basic_weather_forecast.features.home.datasource.network.HomeForecastDataSourceNetwork
import com.example.basic_weather_forecast.features.whole_day.datasource.WholeDayForecastRepository
import com.example.basic_weather_forecast.features.whole_day.datasource.network.WholeDayForecastApi
import com.example.basic_weather_forecast.features.whole_day.datasource.network.WholeDayForecastDataSourceNetwork
import com.example.basic_weather_forecast.features.whole_day.domain.GetWholeDayWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class WholeDayForecastDataSourceModule {

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

    @Provides
    fun provideWholeDayRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create()).build()
}