package com.example.basic_weather_forecast.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_weather_forecast.datasource.model.ForecastCurrentWeatherRequestModel
import com.example.basic_weather_forecast.datasource.model.ForecastWholeDayWeatherRequestModel
import com.example.basic_weather_forecast.domain.GetCurrentWeatherUseCase
import com.example.basic_weather_forecast.domain.GetWholeDayWeatherUseCase
import com.example.basic_weather_forecast.domain.model.ForecastCurrentWeatherUiState
import com.example.basic_weather_forecast.domain.model.ForecastWholeDayWeatherUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastMainViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
) : ViewModel() {

    private val _currentWeatherUiState = MutableStateFlow<ForecastCurrentWeatherUiState>(
        ForecastCurrentWeatherUiState.Nothing
    )
    val currentWeatherUiState: StateFlow<ForecastCurrentWeatherUiState> = _currentWeatherUiState

    fun getCurrentWeather(cityName: String) {
        viewModelScope.launch {
            val apiKey = "5966d26e22e0a37b27f4186cd9df1a4b"
            _currentWeatherUiState.value = ForecastCurrentWeatherUiState.Loading
            try {
                getCurrentWeatherUseCase.execute(
                    ForecastCurrentWeatherRequestModel(cityName, apiKey)
                ).collect { response ->
                    _currentWeatherUiState.value = ForecastCurrentWeatherUiState.Success(response)
                }
            } catch (e: Exception) {
                _currentWeatherUiState.value =
                    ForecastCurrentWeatherUiState.Error("Unexpected error: ${e.message}")
            }
        }
    }
}