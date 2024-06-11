package com.example.basic_weather_forecast.common.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Main(
    @SerializedName("temp") val temp: Double? = null,
    @SerializedName("feels_like") val feelsLike: Double? = null,
    @SerializedName("temp_min") val tempMin: Double? = null,
    @SerializedName("temp_max") val tempMax: Double? = null,
    @SerializedName("pressure") val pressure: Long? = null,
    @SerializedName("humidity") val humidity: Long? = null,
    @SerializedName("sea_level") val seaLevel: Long? = null,
    @SerializedName("grnd_level") val grndLevel: Long? = null,
) : Parcelable
