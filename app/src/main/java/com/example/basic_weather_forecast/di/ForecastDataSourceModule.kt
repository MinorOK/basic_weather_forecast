package com.example.basic_weather_forecast.di

import android.content.Context
import com.example.basic_weather_forecast.features.home.datasource.ForecastRepository
import com.example.basic_weather_forecast.features.home.datasource.network.ForecastApi
import com.example.basic_weather_forecast.features.home.datasource.network.ForecastDataSourceNetwork
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ForecastDataSourceModule {

    @Provides
    fun provideApi(retrofit: Retrofit): ForecastApi = retrofit.create(ForecastApi::class.java)

    @Provides
    fun provideForecastRepository(
        dataSourceNetwork: ForecastDataSourceNetwork
    ) = ForecastRepository(dataSourceNetwork)

    @Provides
    fun provideForecastCurrentWeatherUseCase(
        forecastRepository: ForecastRepository
    ) = GetCurrentWeatherUseCase(forecastRepository)

    @Provides
    fun provideForecastWholeDayWeatherUseCase(
        forecastRepository: ForecastRepository
    ) = GetWholeDayWeatherUseCase(forecastRepository)

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideSetMyStringUseCase(@ApplicationContext context: Context) =
        SetCityNameUseCase(context)

    @Provides
    @Singleton
    fun provideGetMyStringUseCase(@ApplicationContext context: Context) =
        GetCityNameUseCase(context)
}