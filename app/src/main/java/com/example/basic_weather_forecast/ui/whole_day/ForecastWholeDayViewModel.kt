package com.example.basic_weather_forecast.ui.whole_day

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForecastWholeDayViewModel @Inject constructor(
    private val getWholeDayWeatherUseCase: GetWholeDayWeatherUseCase
) :
    ViewModel() {
    private val _wholeDayWeatherUiState =
        MutableStateFlow<ForecastWholeDayWeatherUiState>(ForecastWholeDayWeatherUiState.Nothing)
    val wholeDayWeatherUiState: StateFlow<ForecastWholeDayWeatherUiState> = _wholeDayWeatherUiState

    fun getWholeDayWeather(cityName: String) {
        viewModelScope.launch {
            val apiKey = "5966d26e22e0a37b27f4186cd9df1a4b"
            try {
                _wholeDayWeatherUiState.value = ForecastWholeDayWeatherUiState.Loading
                delay(1000)
                getWholeDayWeatherUseCase.execute(
                    ForecastWholeDayWeatherRequestModel(cityName, apiKey)
                ).collect { response ->
                    response.let {
                        _wholeDayWeatherUiState.value =
                            ForecastWholeDayWeatherUiState.Success(response)
                    }
                }
            } catch (e: Exception) {
                _wholeDayWeatherUiState.value = ForecastWholeDayWeatherUiState.Error
            }
        }
    }
}