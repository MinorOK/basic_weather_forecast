package com.example.basic_weather_forecast.common.mock

import com.example.basic_weather_forecast.features.home.datasource.model.Clouds
import com.example.basic_weather_forecast.features.home.datasource.model.Coord
import com.example.basic_weather_forecast.features.home.datasource.model.HomeCurrentWeatherResponseModel
import com.example.basic_weather_forecast.features.home.datasource.model.Main
import com.example.basic_weather_forecast.features.home.datasource.model.Rain
import com.example.basic_weather_forecast.features.home.datasource.model.Sys
import com.example.basic_weather_forecast.features.home.datasource.model.Weather
import com.example.basic_weather_forecast.features.home.datasource.model.Wind
import com.example.basic_weather_forecast.features.whole_day.datasource.model.ListElement
import com.example.basic_weather_forecast.features.whole_day.datasource.model.WholeDayWeatherResponseModel

object ResponseMock {
    val homeCurrentWeather = HomeCurrentWeatherResponseModel(
        coord = Coord(
            lon = 100.5167, lat = 13.75
        ),
        weather = listOf(
            Weather(
                id = 804,
                main = "Clouds",
                description = "overcast clouds",
                icon = "",
            )
        ),
        base = "stations",
        main = Main(
            temp = 300.22,
            feelsLike = 305.12,
            tempMin = 299.21,
            tempMax = 300.33,
            pressure = 1008,
            humidity = 99,
            seaLevel = 1008,
            grndLevel = 1007
        ),
        visibility = 7152,
        wind = Wind(speed = 3.24, deg = 133, gust = 6.88),
        rain = null,
        clouds = Clouds(all = 100),
        dt = 1716511831,
        sys = Sys(
            type = 2, id = 2090634, country = "TH", sunrise = 1716504596, sunset = 1716550797
        ),
        timezone = 25200,
        id = 1609350,
        name = "Bangkok",
        cod = 200
    )

}