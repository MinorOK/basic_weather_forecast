package com.example.basic_weather_forecast

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.basic_weather_forecast.ui.ForecastApp
import com.example.basic_weather_forecast.ui.settings.SearchStringViewModel
import com.example.basic_weather_forecast.utils.FirstRunUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastActivity : ComponentActivity() {
    private val viewModel: SearchStringViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirstRunUtil.isFirstRun(this)) {
            Log.d("Minot", "First run detected")
            initializeDatabase()
            FirstRunUtil.setFirstRunCompleted(this)
        } else {
            Log.d("Minot", "Not first run")
        }

        setContent {
            ForecastApp()
        }
    }

    private fun initializeDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setSearchStringWithAwait("Bangkok")
        }
    }
}