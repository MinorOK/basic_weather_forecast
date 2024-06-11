package com.example.basic_weather_forecast.common.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ErrorResponse(
    @SerializedName("cod") val cod: String, @SerializedName("message") val message: String
) : Parcelable
