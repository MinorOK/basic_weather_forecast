package com.example.basic_weather_forecast.features.whole_day.datasource.model

import android.os.Parcelable
import com.example.basic_weather_forecast.features.home.datasource.model.Clouds
import com.example.basic_weather_forecast.features.home.datasource.model.Coord
import com.example.basic_weather_forecast.features.home.datasource.model.Main
import com.example.basic_weather_forecast.features.home.datasource.model.Rain
import com.example.basic_weather_forecast.features.home.datasource.model.Sys
import com.example.basic_weather_forecast.features.home.datasource.model.Weather
import com.example.basic_weather_forecast.features.home.datasource.model.Wind
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WholeDayWeatherResponseModel(
    @SerializedName("cod")
    val cod: String,
    @SerializedName("message")
    val message: Long,
    @SerializedName("cnt")
    val cnt: Long,
    @SerializedName("list")
    val list: List<ListElement>,
    @SerializedName("city")
    val city: City
) : Parcelable

@Parcelize
data class City(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("country")
    val country: String,
    @SerializedName("population")
    val population: Long,
    @SerializedName("timezone")
    val timezone: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long
) : Parcelable


@Parcelize
data class ListElement(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("visibility")
    val visibility: Long,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("rain")
    val rain: Rain? = null,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("dt_txt")
    val dtTxt: String
) : Parcelable