package com.example.basic_weather_forecast.features.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.common.ui.WeatherIcon
import com.example.basic_weather_forecast.common.model.Clouds
import com.example.basic_weather_forecast.common.model.Coord
import com.example.basic_weather_forecast.features.home.datasource.model.HomeForecastResponseModel
import com.example.basic_weather_forecast.common.model.Main
import com.example.basic_weather_forecast.common.model.Sys
import com.example.basic_weather_forecast.common.model.Weather
import com.example.basic_weather_forecast.common.model.Wind
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toCelsius
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toFahrenheit

@Composable
fun ForecastMainScreenBody(
    weather: HomeForecastResponseModel?,
    navigateToWholeDayScreen: (String) -> Unit,
    cityName: String,
    isCelsiusState: State<Boolean>
) {
    val isCelsius by isCelsiusState
    val myList: List<String> = listOf(
        "01d",
        "02d",
        "03d",
        "04d",
        "09d",
        "10d",
        "11d",
        "13d",
        "50d",
        "01n",
        "02n",
        "03n",
        "04n",
        "09n",
        "10n",
        "11n",
        "13n",
        "50n"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = temperatureConverter(
                isCelsius = isCelsius,
                temperature = weather?.main?.temp ?: 0.0,
            ),
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 96.sp,
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Text(
                text = "${weather?.weather?.get(0)?.description}",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                )
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            Text(
                text = "${
                    tempConverter(isCelsius, weather?.main?.tempMin)
                } / ${tempConverter(isCelsius, weather?.main?.tempMax)}",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                )
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        WeatherHourlyBox(myList, navigateToWholeDayScreen, cityName)
        WeatherGridBoxDetailFirstRow(weather)
        WeatherGridBoxDetailSecondRow(weather = weather, isCelsius = isCelsius)
    }
}

@Composable
fun temperatureConverter(
    isCelsius: Boolean,
    temperature: Double
): String {
    return temperature.let {
        if (isCelsius) "${it.toCelsius()}${stringResource(R.string.weather_unit)}"
        else "${it.toFahrenheit()}${stringResource(R.string.weather_unit)}"
    }
}

@Composable
fun WeatherGridBoxDetailSecondRow(
    weather: HomeForecastResponseModel?,
    isCelsius: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        ) {
            gridBoxDetail(
                painter = painterResource(id = R.drawable.ic_air_pressure),
                title = "ความกดอากาศ",
                value = weather?.main?.pressure.toString(),
                valueUnit = "hPa"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        ) {
            gridBoxDetail(
                painter = painterResource(id = R.drawable.ic_sun),
                title = "รู้สึกเหมือน",
                value =
                temperatureConverter(
                    isCelsius = isCelsius,
                    temperature = weather?.main?.feelsLike ?: 0.0
                ),
                valueUnit = ""
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        ) {
            gridBoxDetail(
                painter = painterResource(id = R.drawable.ic_visibility),
                title = "ทัศนวิสัย",
                value = (weather?.visibility?.div(1000)).toString(),
                valueUnit = "กม."
            )
        }
    }
}

@Composable
fun WeatherGridBoxDetailFirstRow(
    weather: HomeForecastResponseModel?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Surface(
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        ) {
            gridBoxDetail(
                painter = painterResource(id = R.drawable.ic_wind),
                title = "ลม",
                value = weather?.wind?.speed.toString(),
                valueUnit = "กม./ชม."
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        ) {
            gridBoxDetail(
                painter = painterResource(id = R.drawable.ic_humidity),
                title = "ความชื้น",
                value = weather?.main?.humidity.toString(),
                valueUnit = "%"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            color = Color.White,
            modifier = Modifier
                .weight(1f)
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        ) {
            gridBoxDetail(
                painter = painterResource(id = R.drawable.ic_visibility),
                title = "ทัศนวิสัย",
                value = (weather?.visibility?.div(1000)).toString(),
                valueUnit = "กม."
            )
        }
    }
}

@Composable
fun gridBoxDetail(painter: Painter, title: String, value: String, valueUnit: String) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(12.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            colorFilter = ColorFilter.tint(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            color = Color.Black,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = value,
                color = Color.Black,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
            )
            Text(
                text = " $valueUnit",
                color = Color.Black,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                ),
            )
        }
    }
}

@Composable
fun WeatherHourlyBox(
    myList: List<String>,
    navigateToWholeDayScreen: (String) -> Unit,
    cityName: String,
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        tonalElevation = 4.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable {
                navigateToWholeDayScreen(cityName)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            LazyRow {
                items(myList) { icon ->
                    HourlyWeatherForecast(icon)
                }
            }
        }
    }
}

@Composable
fun HourlyWeatherForecast(icon: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(end = 12.dp)
    ) {
        Text(
            "08:00 น.", color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
            ),
        )
        WeatherIcon(icon = icon)
        Text(
            "30°",
            color = Color.Black,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

@Composable
fun tempConverter(isCelsius: Boolean, temp: Double?): String {
    if (isCelsius) {
        return "${temp.toCelsius().toString()}${stringResource(R.string.weather_unit)}"
    }
    return "${temp.toFahrenheit().toString()}${stringResource(R.string.weather_unit)}"
}

@Preview
@Composable
fun ForecastMainCurrentWeatherComponentPreview() {
    ForecastMainScreenBody(
        HomeForecastResponseModel(
            coord = Coord(
                lon = 100.5167,
                lat = 13.75
            ),
            weather =
            listOf(
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
                type = 2,
                id = 2090634,
                country = "TH",
                sunrise = 1716504596,
                sunset = 1716550797
            ),
            timezone = 25200,
            id = 1609350,
            name = "Bangkok",
            cod = 200
        ),
        {},
        "Bangkok",
        mutableStateOf(true),
    )
}

