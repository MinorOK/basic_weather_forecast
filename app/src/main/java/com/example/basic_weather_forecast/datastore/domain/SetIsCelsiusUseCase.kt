package com.example.basic_weather_forecast.datastore.domain

import com.example.basic_weather_forecast.datastore.PreferencesRepository
import javax.inject.Inject

class SetIsCelsiusUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(isCelsius: Boolean) {
        preferencesRepository.setCelsius(isCelsius)
    }
}