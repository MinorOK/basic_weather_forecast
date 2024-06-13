package com.example.basic_weather_forecast.features.home.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_weather_forecast.datastore.share_preferences.domain.GetCityNameUseCase
import com.example.basic_weather_forecast.datastore.share_preferences.domain.GetIsCelsiusUseCase
import com.example.basic_weather_forecast.datastore.share_preferences.domain.SetCityNameUseCase
import com.example.basic_weather_forecast.datastore.share_preferences.domain.SetIsCelsiusUseCase
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.GeocodingResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherRequestModel
import com.example.basic_weather_forecast.features.home.datasource.model.HomeOneCallRequestModel
import com.example.basic_weather_forecast.features.home.domain.GetCurrentWeatherUseCase
import com.example.basic_weather_forecast.features.home.domain.GetGeocodeUseCase
import com.example.basic_weather_forecast.features.home.domain.GetOneCallUseCase
import com.example.basic_weather_forecast.features.home.domain.model.HomeForecastCurrentWeatherUiState
import com.example.basic_weather_forecast.features.home.domain.model.HomeGeocodeUiState
import com.example.basic_weather_forecast.features.home.domain.model.HomeOneCallUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getGeocodeUseCase: GetGeocodeUseCase,
    private val getOneCallUseCase: GetOneCallUseCase,
    getIsCelsiusUseCase: GetIsCelsiusUseCase,
    private val setIsCelsiusUseCase: SetIsCelsiusUseCase,
    private val setCityNameUseCase: SetCityNameUseCase,
    private val getCityNameUseCase: GetCityNameUseCase
) : ViewModel() {

    private val _currentWeatherUiState = MutableStateFlow<HomeForecastCurrentWeatherUiState>(
        HomeForecastCurrentWeatherUiState.Nothing
    )
    val currentWeatherUiState: StateFlow<HomeForecastCurrentWeatherUiState> = _currentWeatherUiState

    private val _geocodeUiState = MutableStateFlow<HomeGeocodeUiState>(HomeGeocodeUiState.Loading)

    private val _geocodeResponse = MutableStateFlow<List<GeocodingResponseModel>>(emptyList())

    private val _oneCallUiState = MutableStateFlow<HomeOneCallUiState>(HomeOneCallUiState.Loading)
    val oneCallUiState: StateFlow<HomeOneCallUiState> = _oneCallUiState

    val isCelsius: StateFlow<Boolean> =
        getIsCelsiusUseCase().stateIn(viewModelScope, SharingStarted.Eagerly, true)

    private val _apiKey = "5966d26e22e0a37b27f4186cd9df1a4b"

    fun setCelsius(isCelsius: Boolean) {
        viewModelScope.launch {
            setIsCelsiusUseCase(isCelsius)
        }
    }

    suspend fun setSearchString(value: String): Unit = suspendCancellableCoroutine { continuation ->
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setCityNameUseCase(value)
                continuation.resume(Unit)
            } catch (e: Exception) {
                Log.e("ForecastLog", "Failed to set search string", e)
            }
        }
    }

    suspend fun getSearchString(): String? = suspendCancellableCoroutine { continuation ->
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getCityNameUseCase()
                continuation.resume(result)
            } catch (e: Exception) {
                Log.e("ForecastLog", "Failed to get search string", e)
            }
        }
    }

    fun getCurrentWeather(cityName: String) {
//        runCatching {
//
//        }.onSuccess {
//
//        }.onFailure {
//
//        }

        viewModelScope.launch {
            try {
                getCurrentWeatherUseCase.execute(
                    HomeCurrentWeatherRequestModel(cityName, _apiKey)
                ).onStart {
                    _currentWeatherUiState.value = HomeForecastCurrentWeatherUiState.Loading
                }.collect { response ->
                    _currentWeatherUiState.value =
                        HomeForecastCurrentWeatherUiState.Success(response)
                }
            } catch (e: Exception) {
                _currentWeatherUiState.value =
                    HomeForecastCurrentWeatherUiState.Error("${e.message}")
            }
        }
    }

    private suspend fun fetchGeocode(cityName: String): List<GeocodingResponseModel> {
        return try {
            val response = getGeocodeUseCase.execute(
                GeocodingRequestModel(cityName, "5", _apiKey)
            )
            response.collect { geocodes ->
                _geocodeResponse.value = geocodes
            }
            _geocodeResponse.value
        } catch (e: Exception) {
            _geocodeUiState.value = HomeGeocodeUiState.Error("${e.message}")
            emptyList()
        }
    }

    fun getWeatherWithOneCall(cityName: String) {
        viewModelScope.launch {
            _geocodeUiState.value = HomeGeocodeUiState.Loading
            _oneCallUiState.value = HomeOneCallUiState.Loading

            val geocodes = fetchGeocode(cityName)
            if (geocodes.isNotEmpty()) {
                val firstGeocode = geocodes[0]
                getOneCall(firstGeocode.lat ?: 0.0, firstGeocode.lon ?: 0.0)
            } else {
                _oneCallUiState.value = HomeOneCallUiState.Error("Geocode failed to fetch data")
            }
        }
    }

    private fun getOneCall(lat: Double, long: Double) {
        viewModelScope.launch {
            try {
                getOneCallUseCase.execute(
                    HomeOneCallRequestModel(lat, long, _apiKey)
                ).collect { response ->
                    _oneCallUiState.value = HomeOneCallUiState.Success(response)
                    Log.d("ForecastLog", "One Call:\n $response")
                }
            } catch (e: Exception) {
                _oneCallUiState.value = HomeOneCallUiState.Error("${e.message}")
            }
        }
    }
}