package com.example.basic_weather_forecast.features.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastRequestModel
import com.example.basic_weather_forecast.features.home.domain.GetCurrentWeatherUseCase
import com.example.basic_weather_forecast.features.home.domain.GetGeocodeUseCase
import com.example.basic_weather_forecast.features.home.domain.model.HomeForecastCurrentWeatherUiState
import com.example.basic_weather_forecast.features.home.domain.model.HomeGeocodeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getGeocodeUseCase: GetGeocodeUseCase
) : ViewModel() {

    private val _currentWeatherUiState = MutableStateFlow<HomeForecastCurrentWeatherUiState>(
        HomeForecastCurrentWeatherUiState.Nothing
    )
    val currentWeatherUiState: StateFlow<HomeForecastCurrentWeatherUiState> = _currentWeatherUiState

    private val _geocodeUiState =
        MutableStateFlow<HomeGeocodeUiState>(HomeGeocodeUiState.Loading)

    val geocodeUiState: StateFlow<HomeGeocodeUiState> = _geocodeUiState

    fun getCurrentWeather(cityName: String) {
        viewModelScope.launch {
            val apiKey = "5966d26e22e0a37b27f4186cd9df1a4b"
            _currentWeatherUiState.value = HomeForecastCurrentWeatherUiState.Loading
            try {
                getCurrentWeatherUseCase.execute(
                    HomeForecastRequestModel(cityName, apiKey)
                ).collect { response ->
                    _currentWeatherUiState.value =
                        HomeForecastCurrentWeatherUiState.Success(response)
                }
            } catch (e: Exception) {
                _currentWeatherUiState.value =
                    HomeForecastCurrentWeatherUiState.Error("Unexpected error: ${e.message}")
            }
        }
    }

    fun getGeocode(cityName: String, limit: String) {
        viewModelScope.launch {
            val apiKey = "5966d26e22e0a37b27f4186cd9df1a4b"
            _geocodeUiState.value = HomeGeocodeUiState.Loading
            try {
                getGeocodeUseCase.execute(
                    GeocodingRequestModel(cityName, limit, apiKey)
                ).collect { response ->
                    _geocodeUiState.value =
                        HomeGeocodeUiState.Success(response)
                    Log.d("ForecastLog", "$response")
                }
            } catch (e: Exception) {
                _geocodeUiState.value =
                    HomeGeocodeUiState.Error("Unexpected error: ${e.message}")
                Log.d("ForecastLog", "${e.message}")
            }
        }
    }
}