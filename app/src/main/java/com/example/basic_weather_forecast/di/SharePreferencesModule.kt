package com.example.basic_weather_forecast.di

import android.content.Context
import com.example.basic_weather_forecast.datastore.sharePreferences.PreferencesRepository
import com.example.basic_weather_forecast.datastore.sharePreferences.domain.GetCityNameUseCase
import com.example.basic_weather_forecast.datastore.sharePreferences.domain.GetIsCelsiusUseCase
import com.example.basic_weather_forecast.datastore.sharePreferences.domain.SetCityNameUseCase
import com.example.basic_weather_forecast.datastore.sharePreferences.domain.SetIsCelsiusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharePreferencesModule {
    @Provides
    @Singleton
    fun provideSetMyStringUseCase(@ApplicationContext context: Context) =
        SetCityNameUseCase(context)

    @Provides
    @Singleton
    fun provideGetMyStringUseCase(@ApplicationContext context: Context) =
        GetCityNameUseCase(context)

    @Provides
    @Singleton
    fun provideSetIsCelsiusUseCase(preferencesRepository: PreferencesRepository) =
        SetIsCelsiusUseCase(preferencesRepository)

    @Provides
    @Singleton
    fun provideGetIsCelsiusUseCase(preferencesRepository: PreferencesRepository) =
        GetIsCelsiusUseCase(preferencesRepository)
}