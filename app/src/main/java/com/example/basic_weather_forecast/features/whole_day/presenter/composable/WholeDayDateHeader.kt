package com.example.basic_weather_forecast.features.whole_day.presenter.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.basic_weather_forecast.common.theme.Typography
import com.example.basic_weather_forecast.common.theme.secondaryColor

@Composable
fun WholeDayDateHeader(date: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = secondaryColor,
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .padding(top = 8.dp)
    ) {
        Text(
            text = date,
            style = Typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}