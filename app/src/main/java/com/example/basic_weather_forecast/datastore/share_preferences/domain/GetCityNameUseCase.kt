package com.example.basic_weather_forecast.datastore.share_preferences.domain

import android.content.Context

class GetCityNameUseCase(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    operator fun invoke(): String? {
        return sharedPreferences.getString(KEY_MY_STRING, null)
    }

    companion object {
        private const val PREF_NAME = "my_pref"
        private const val KEY_MY_STRING = "my_string"
    }
}