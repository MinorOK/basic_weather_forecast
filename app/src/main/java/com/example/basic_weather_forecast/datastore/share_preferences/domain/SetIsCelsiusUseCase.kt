package com.example.basic_weather_forecast.datastore.share_preferences.domain

import com.example.basic_weather_forecast.datastore.share_preferences.PreferencesRepository
import javax.inject.Inject

class SetIsCelsiusUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    suspend operator fun invoke(isCelsius: Boolean) {
        preferencesRepository.setCelsius(isCelsius)
    }
}