package com.example.basic_weather_forecast.ui.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.navigation.NavigationDestination
import com.example.basic_weather_forecast.ui.ForecastAppBar

object ForecastSettingsDestination : NavigationDestination {
    override val route: String
        get() = "settings"
    override val titleRes: Int
        get() = R.string.settings_screen

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastSettingsScreen(
    navigateBack: () -> Unit, modifier: Modifier = Modifier,
    settingsPreferencesViewModel: SettingsPreferencesViewModel = hiltViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val isCelsius by settingsPreferencesViewModel.isCelsius.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ForecastAppBar(
                title = stringResource(ForecastSettingsDestination.titleRes),
                navigateBackIcon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                canNavigateBack = true,
                navigateUp = { navigateBack() },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color(0xFF2E335A)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                tonalElevation = 4.dp,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "อุณหภูมิ",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isCelsius)
                                Text(
                                    text = "เซลเซียส (${stringResource(R.string.weather_unit_celsius)})",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                    ),
                                )
                            else
                                Text(
                                    text = "เซลเซียส (${stringResource(R.string.weather_unit_fahrenheit)})",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                    ),
                                )
                            Spacer(modifier = Modifier.width(6.dp))
                            Switch(
                                checked = isCelsius,
                                onCheckedChange = {
                                    settingsPreferencesViewModel.setCelsius(it)
                                    Log.d("Minoy", "Celsius: $it")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}