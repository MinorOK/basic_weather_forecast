package com.example.basic_weather_forecast.common.utils

import android.content.Context
import android.content.SharedPreferences

object FirstRunUtil {
    private const val PREFS_NAME = "app_prefs"
    private const val FIRST_RUN_KEY = "first_run"

    fun isFirstRun(context: Context): Boolean {
        val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return preferences.getBoolean(FIRST_RUN_KEY, true)
    }

    fun setFirstRunCompleted(context: Context) {
        val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        preferences.edit().putBoolean(FIRST_RUN_KEY, false).apply()
    }
}