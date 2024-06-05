package com.example.basic_weather_forecast.datastore.domain

import com.example.basic_weather_forecast.datastore.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsCelsiusUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return preferencesRepository.isCelsius
    }
}