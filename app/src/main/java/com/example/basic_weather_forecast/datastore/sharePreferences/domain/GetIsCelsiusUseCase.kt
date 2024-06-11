package com.example.basic_weather_forecast.datastore.sharePreferences.domain

import com.example.basic_weather_forecast.datastore.sharePreferences.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsCelsiusUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return preferencesRepository.isCelsius
    }
}