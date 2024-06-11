package com.example.basic_weather_forecast

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.basic_weather_forecast.features.settings.presentation.SearchStringViewModel
import com.example.basic_weather_forecast.common.utils.FirstRunUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForecastActivity : ComponentActivity() {
    private val viewModel: SearchStringViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIsFirstRun()
        setContent {
            ForecastApp()
        }
    }

    private fun checkIsFirstRun() {
        if (FirstRunUtil.isFirstRun(this)) {
            initializeDatabase()
            FirstRunUtil.setFirstRunCompleted(this)
        } else {
            Log.d("ForecastLog", "Not first run")
        }
    }

    private fun initializeDatabase() {
        Log.d("ForecastLog", "First run")
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.setSearchString("Bangkok")
        }
    }
}