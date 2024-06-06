package com.example.basic_weather_forecast.common.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.basic_weather_forecast.R

@Composable
fun WeatherIcon(icon: String) {
    val resourceId = getDrawableResourceId(icon)
    if (resourceId != null)
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = null,
//            colorFilter = ColorFilter.tint(color = Color.Red)
        )
}

fun getDrawableResourceId(icon: String): Int? {
    val resourceId = IconMapper.getDrawableResourceId(icon)
    return if (resourceId != 0) resourceId else null
}

object IconMapper {
    private val iconMap = mapOf(
        "01d" to R.drawable.ic_01d,
        "01n" to R.drawable.ic_01n,
        "02d" to R.drawable.ic_02d,
        "02n" to R.drawable.ic_02n,
        "03d" to R.drawable.ic_01d,
        "03n" to R.drawable.ic_01n,
        "04d" to R.drawable.ic_02d,
        "04n" to R.drawable.ic_02n,
        "09d" to R.drawable.ic_01d,
        "09n" to R.drawable.ic_01n,
        "10d" to R.drawable.ic_02d,
        "10n" to R.drawable.ic_02n,
        "11d" to R.drawable.ic_01d,
        "11n" to R.drawable.ic_01n,
        "13d" to R.drawable.ic_02d,
        "13n" to R.drawable.ic_02n,
        "50d" to R.drawable.ic_01d,
        "50n" to R.drawable.ic_01n,
    )

    fun getDrawableResourceId(icon: String): Int? {
        return iconMap[icon]
    }
}