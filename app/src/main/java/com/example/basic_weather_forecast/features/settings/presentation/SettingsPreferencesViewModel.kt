package com.example.basic_weather_forecast.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_weather_forecast.datastore.sharePreferences.domain.GetIsCelsiusUseCase
import com.example.basic_weather_forecast.datastore.sharePreferences.domain.SetIsCelsiusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsPreferencesViewModel @Inject constructor(
    getIsCelsiusUseCase: GetIsCelsiusUseCase,
    private val setIsCelsiusUseCase: SetIsCelsiusUseCase
) : ViewModel() {
    val isCelsius: StateFlow<Boolean> = getIsCelsiusUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun setCelsius(isCelsius: Boolean) {
        viewModelScope.launch {
            setIsCelsiusUseCase(isCelsius)
        }
    }
}