package com.example.basic_weather_forecast.features.whole_day.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basic_weather_forecast.ForecastAppBar
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.common.ui.WeatherIcon
import com.example.basic_weather_forecast.common.utils.FormatterUtil.groupByDate
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toCelsius
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toFahrenheit
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toTimeString
import com.example.basic_weather_forecast.features.home.presentation.HomeViewModel
import com.example.basic_weather_forecast.features.whole_day.datasource.model.ListElement
import com.example.basic_weather_forecast.features.whole_day.domain.model.WholeDayForecastUiState
import com.example.basic_weather_forecast.navigation.NavigationDestination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

object ForecastWholeDayDestination : NavigationDestination {
    const val cityNameArg = "cityName"
    override val route: String
        get() = "whole_day/{$cityNameArg}"
    override val titleRes: Int
        get() = R.string.whole_day_screen

    fun createRoute(cityName: String): String {
        return "whole_day/$cityName"
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastWholeDayScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WholeDayViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val wholeDayWeatherUiState by viewModel.wholeDayWeatherUiState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isCelsiusState = homeViewModel.isCelsius.collectAsState()
    val isCelsius by isCelsiusState
    var cityName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(homeViewModel) {
        cityName = homeViewModel.getSearchString()
        cityName?.let {
            viewModel.getWholeDayWeather(it)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ForecastAppBar(
                title = stringResource(ForecastWholeDayDestination.titleRes),
                canNavigateBack = true,
                navigateBackIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigateUp = navigateBack,
            )
        },
    ) { innerPadding ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                isRefreshing = true
                viewModel.getWholeDayWeather(cityName ?: "")
                MainScope().launch {
                    isRefreshing = false
                }
            },
        ) {
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .height(1000.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .height(1000.dp)
                        .background(color = Color(0xFF2E335A)),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    when (wholeDayWeatherUiState) {
                        is WholeDayForecastUiState.Success -> {
                            val weatherList =
                                (wholeDayWeatherUiState as WholeDayForecastUiState.Success).data.list
                            GroupedWeatherList(
                                weatherList = weatherList,
                                isCelsius = isCelsius
                            )
                        }

                        is WholeDayForecastUiState.Error -> {
                            Text(text = stringResource(R.string.weather_data_error_loading))
                        }

                        is WholeDayForecastUiState.Loading -> {
                            CircularProgressIndicator()
                        }

                        is WholeDayForecastUiState.Nothing -> {
                            Text(text = stringResource(R.string.weather_data_is_empty))
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun GroupedWeatherList(
    weatherList: List<ListElement>,
    isCelsius: Boolean
) {
    val groupedWeather = weatherList.groupByDate()

    LazyColumn {
        groupedWeather.forEach { (date, weatherForDate) ->
            item {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White,
                    tonalElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = date,
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
            items(weatherForDate) { weatherItem ->
                HourlyForecastListItem(
                    weatherList = weatherItem,
                    isCelsius = isCelsius
                )
            }
        }
    }
}

@Composable
fun HourlyForecastListItem(
    weatherList: ListElement,
    isCelsius: Boolean
) {
    val temperatureMainText = temperatureConverter(
        isCelsius = isCelsius,
        temperature = weatherList.main.temp ?: 0.0
    )
    val temperatureFeelLikesText = temperatureConverter(
        isCelsius = isCelsius,
        temperature = weatherList.main.feelsLike ?: 0.0
    )


    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = weatherList.dt.toTimeString(),
                            style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp,
                            )
                        )
                        WeatherIcon(icon = weatherList.weather[0].icon.toString())
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = weatherList.weather[0].description.toString(),
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                        )
                    )
                }
                Text(
                    text = temperatureMainText,
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            ForecastDetail(
                title = stringResource(R.string.weather_feels_like),
                detail = temperatureFeelLikesText
            )
            HorizontalDivider()
            ForecastDetail(
                title = stringResource(R.string.weather_wind_speed),
                detail = "${weatherList.wind.speed} ${stringResource(R.string.kilo_per_hour)}"
            )
            HorizontalDivider()
            ForecastDetail(
                title = stringResource(R.string.weather_humidity),
                detail = "${weatherList.main.humidity}${stringResource(R.string.percentage)}"
            )
        }
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
fun ForecastDetail(title: String, detail: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        Text(text = detail)
    }
}
