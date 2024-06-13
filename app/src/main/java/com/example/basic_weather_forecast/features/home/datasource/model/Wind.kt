package com.example.basic_weather_forecast.features.home.datasource.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wind(
    @SerializedName("speed") val speed: Double? = null,
    @SerializedName("deg") val deg: Long? = null,
    @SerializedName("gust") val gust: Double? = null,
) : Parcelable