package com.example.basic_weather_forecast.features.home.presenter.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.common.theme.Typography
import com.example.basic_weather_forecast.features.home.presenter.HomeViewModel

@Composable
fun HomeSettingsContent(
    viewModel: HomeViewModel
) {
    val isCelsius by viewModel.isCelsius.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.temperature),
                    style = Typography.bodyMedium,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isCelsius) Text(
                        text = "${stringResource(R.string.weather_celsius)} (${stringResource(R.string.weather_unit_celsius)})",
                        style = Typography.labelMedium,
                    )
                    else Text(
                        text = "${stringResource(R.string.weather_fahrenheit)} (${
                            stringResource(
                                R.string.weather_unit_fahrenheit
                            )
                        })",
                        style = Typography.labelMedium,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Switch(checked = isCelsius, onCheckedChange = {
                        viewModel.setCelsius(it)
                    })
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeSettingsContent() {
    HomeGridBoxDetail(
        painter = painterResource(id = R.drawable.ic_wind),
        title = stringResource(R.string.weather_wind_speed),
        value = "3.64",
        valueUnit = stringResource(R.string.kilo_per_hour)
    )
}