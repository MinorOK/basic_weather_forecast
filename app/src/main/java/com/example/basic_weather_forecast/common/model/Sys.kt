package com.example.basic_weather_forecast.common.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sys(
    @SerializedName("type")
    val type: Long? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("sunrise")
    val sunrise: Long? = null,
    @SerializedName("sunset")
    val sunset: Long? = null,
) : Parcelable
