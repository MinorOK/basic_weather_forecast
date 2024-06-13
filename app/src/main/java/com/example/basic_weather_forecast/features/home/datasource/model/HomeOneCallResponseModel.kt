package com.example.basic_weather_forecast.features.home.datasource.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeOneCallResponseModel(
    @SerializedName("lat") val lat: Double? = null,
    @SerializedName("lon") val lon: Double? = null,
    @SerializedName("timezone") val timezone: String? = null,
    @SerializedName("timezone_offset") val timezoneOffset: Long? = null,
    @SerializedName("current") val current: Current? = null,
    @SerializedName("hourly") val hourly: List<Hourly>? = null,
    @SerializedName("daily") val daily: List<Daily>? = null
) : Parcelable

@Parcelize
data class Current(
    @SerializedName("dt") val dt: Long? = null,
    @SerializedName("sunrise") val sunrise: Long? = null,
    @SerializedName("sunset") val sunset: Long? = null,
    @SerializedName("temp") val temp: Double? = null,
    @SerializedName("feels_like") val feelsLike: Double? = null,
    @SerializedName("pressure") val pressure: Long? = null,
    @SerializedName("humidity") val humidity: Long? = null,
    @SerializedName("dew_point") val dewPoint: Double? = null,
    @SerializedName("uvi") val uvi: Double? = null,
    @SerializedName("clouds") val clouds: Long? = null,
    @SerializedName("visibility") val visibility: Long? = null,
    @SerializedName("wind_speed") val windSpeed: Double? = null,
    @SerializedName("wind_deg") val windDeg: Long? = null,
    @SerializedName("wind_gust") val windGust: Double? = null,
    @SerializedName("weather") val weather: List<Weather>? = null
) : Parcelable

@Parcelize
data class Daily(
    @SerializedName("dt") val dt: Long? = null,
    @SerializedName("sunrise") val sunrise: Long? = null,
    @SerializedName("sunset") val sunset: Long? = null,
    @SerializedName("moonrise") val moonrise: Long? = null,
    @SerializedName("moonset") val moonset: Long? = null,
    @SerializedName("moon_phase") val moonPhase: Double? = null,
    @SerializedName("summary") val summary: String? = null,
    @SerializedName("temp") val temp: Temp? = null,
    @SerializedName("feels_like") val feelsLike: FeelsLike? = null,
    @SerializedName("pressure") val pressure: Long? = null,
    @SerializedName("humidity") val humidity: Long? = null,
    @SerializedName("dew_point") val dewPoint: Double? = null,
    @SerializedName("wind_speed") val windSpeed: Double? = null,
    @SerializedName("wind_deg") val windDeg: Long? = null,
    @SerializedName("wind_gust") val windGust: Double? = null,
    @SerializedName("weather") val weather: List<Weather>? = null,
    @SerializedName("clouds") val clouds: Long? = null,
    @SerializedName("pop") val pop: Double? = null,
    @SerializedName("uvi") val uvi: Double? = null,
    @SerializedName("rain") val rain: Double? = null
) : Parcelable

@Parcelize
data class FeelsLike(
    @SerializedName("day") val day: Double? = null,
    @SerializedName("night") val night: Double? = null,
    @SerializedName("eve") val eve: Double? = null,
    @SerializedName("morn") val morn: Double? = null
) : Parcelable

@Parcelize
data class Temp(
    @SerializedName("day") val day: Double? = null,
    @SerializedName("min") val min: Double? = null,
    @SerializedName("max") val max: Double? = null,
    @SerializedName("night") val night: Double? = null,
    @SerializedName("eve") val eve: Double? = null,
    @SerializedName("morn") val morn: Double? = null
) : Parcelable

@Parcelize
data class Hourly(
    @SerializedName("dt") val dt: Long? = null,
    @SerializedName("temp") val temp: Double? = null,
    @SerializedName("feels_like") val feelsLike: Double? = null,
    @SerializedName("pressure") val pressure: Long? = null,
    @SerializedName("humidity") val humidity: Long? = null,
    @SerializedName("dew_point") val dewPoint: Double? = null,
    @SerializedName("uvi") val uvi: Double? = null,
    @SerializedName("clouds") val clouds: Long? = null,
    @SerializedName("visibility") val visibility: Long? = null,
    @SerializedName("wind_speed") val windSpeed: Double? = null,
    @SerializedName("wind_deg") val windDeg: Long? = null,
    @SerializedName("wind_gust") val windGust: Double? = null,
    @SerializedName("weather") val weather: List<Weather>? = null,
    @SerializedName("pop") val pop: Double? = null
) : Parcelable