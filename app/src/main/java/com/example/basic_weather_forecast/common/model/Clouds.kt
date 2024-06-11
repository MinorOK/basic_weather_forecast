package com.example.basic_weather_forecast.common.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Clouds(
    @SerializedName("all") val all: Long? = null,
) : Parcelable