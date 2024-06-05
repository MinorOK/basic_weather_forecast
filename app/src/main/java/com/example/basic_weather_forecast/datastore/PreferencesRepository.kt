package com.example.basic_weather_forecast.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("settings")

@Singleton
class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val IS_CELSIUS_KEY = booleanPreferencesKey("is_celsius")

    val isCelsius: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_CELSIUS_KEY] ?: true
        }

    suspend fun setCelsius(isCelsius: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_CELSIUS_KEY] = isCelsius
        }
    }
}