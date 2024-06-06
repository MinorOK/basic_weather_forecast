package com.example.basic_weather_forecast.di

import android.content.Context
import com.example.basic_weather_forecast.datastore.domain.GetCityNameUseCase
import com.example.basic_weather_forecast.datastore.domain.SetCityNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    @Singleton
    fun provideSetMyStringUseCase(@ApplicationContext context: Context) =
        SetCityNameUseCase(context)

    @Provides
    @Singleton
    fun provideGetMyStringUseCase(@ApplicationContext context: Context) =
        GetCityNameUseCase(context)
}