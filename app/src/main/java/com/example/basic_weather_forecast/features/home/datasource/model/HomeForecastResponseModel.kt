package com.example.basic_weather_forecast.features.home.datasource.model

import android.os.Parcelable
import com.example.basic_weather_forecast.common.model.Clouds
import com.example.basic_weather_forecast.common.model.Coord
import com.example.basic_weather_forecast.common.model.Main
import com.example.basic_weather_forecast.common.model.Rain
import com.example.basic_weather_forecast.common.model.Sys
import com.example.basic_weather_forecast.common.model.Weather
import com.example.basic_weather_forecast.common.model.Wind
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeForecastResponseModel(
    @SerializedName("coord")
    val coord: Coord? = null,
    @SerializedName("weather")
    val weather: List<Weather>? = null,
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("visibility")
    val visibility: Long? = null,
    @SerializedName("wind")
    val wind: Wind? = null,
    @SerializedName("rain")
    val rain: Rain? = null,
    @SerializedName("clouds")
    val clouds: Clouds? = null,
    @SerializedName("dt")
    val dt: Long? = null,
    @SerializedName("sys")
    val sys: Sys? = null,
    @SerializedName("timezone")
    val timezone: Long? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("cod")
    val cod: Long? = null,
) : Parcelable







