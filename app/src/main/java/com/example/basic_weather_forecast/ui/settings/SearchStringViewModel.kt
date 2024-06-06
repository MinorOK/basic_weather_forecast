package com.example.basic_weather_forecast.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_weather_forecast.datastore.domain.GetCityNameUseCase
import com.example.basic_weather_forecast.datastore.domain.SetCityNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class SearchStringViewModel @Inject constructor(
    private val setCityNameUseCase: SetCityNameUseCase,
    private val getCityNameUseCase: GetCityNameUseCase
) : ViewModel() {

    suspend fun setSearchString(value: String): Unit = suspendCancellableCoroutine { continuation ->
        viewModelScope.launch(Dispatchers.IO) {
            try {
                setCityNameUseCase(value)
                continuation.resume(Unit)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    suspend fun getSearchString(): String? = suspendCancellableCoroutine { continuation ->
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = getCityNameUseCase()
                continuation.resume(result)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }
}