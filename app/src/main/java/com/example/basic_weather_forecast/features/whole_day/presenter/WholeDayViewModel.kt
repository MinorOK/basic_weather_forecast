package com.example.basic_weather_forecast.features.whole_day.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayForecastRequestModel
import com.example.basic_weather_forecast.features.whole_day.domain.GetWholeDayWeatherUseCase
import com.example.basic_weather_forecast.features.whole_day.domain.model.WholeDayForecastUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WholeDayViewModel @Inject constructor(
    private val getWholeDayWeatherUseCase: GetWholeDayWeatherUseCase
) :
    ViewModel() {
    private val _wholeDayWeatherUiState =
        MutableStateFlow<WholeDayForecastUiState>(WholeDayForecastUiState.Nothing)
    val wholeDayWeatherUiState: StateFlow<WholeDayForecastUiState> = _wholeDayWeatherUiState

    fun getWholeDayWeather(cityName: String) {
        viewModelScope.launch {
            val apiKey = "5966d26e22e0a37b27f4186cd9df1a4b"
            try {
                getWholeDayWeatherUseCase.execute(
                    WholeDayForecastRequestModel(cityName, apiKey)
                ).onStart {
                    _wholeDayWeatherUiState.value = WholeDayForecastUiState.Loading
                }.collect { response ->
                    response.let {
                        _wholeDayWeatherUiState.value =
                            WholeDayForecastUiState.Success(response)
                    }
                }
            } catch (e: Exception) {
                _wholeDayWeatherUiState.value = WholeDayForecastUiState.Error
            }
        }
    }
}