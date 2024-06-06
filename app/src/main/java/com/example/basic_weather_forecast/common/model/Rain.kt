package com.example.basic_weather_forecast.common.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rain(
    @SerializedName("1h")
    val the1H: Double? = null,
) : Parcelable
