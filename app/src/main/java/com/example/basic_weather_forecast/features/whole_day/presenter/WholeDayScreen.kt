package com.example.basic_weather_forecast.features.whole_day.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.basic_weather_forecast.ForecastAppBar
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.common.theme.Typography
import com.example.basic_weather_forecast.common.theme.primaryColor
import com.example.basic_weather_forecast.common.theme.secondaryColor
import com.example.basic_weather_forecast.common.ui.WeatherIcon
import com.example.basic_weather_forecast.common.utils.FormatterUtil.groupByDate
import com.example.basic_weather_forecast.common.utils.FormatterUtil.toTimeString
import com.example.basic_weather_forecast.features.home.presenter.HomeViewModel
import com.example.basic_weather_forecast.features.home.presenter.composable.HomeTemperatureConverter
import com.example.basic_weather_forecast.features.whole_day.datasource.model.ListElement
import com.example.basic_weather_forecast.features.whole_day.domain.model.WholeDayForecastUiState
import com.example.basic_weather_forecast.features.whole_day.presenter.composable.ForecastDetailTextRowContent
import com.example.basic_weather_forecast.features.whole_day.presenter.composable.WholeDayDateHeader
import com.example.basic_weather_forecast.features.whole_day.presenter.composable.WholeDayGroupedWeatherList
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

//    val navBackStackEntry = rememberNavController().currentBackStackEntry
//    val cityName = navBackStackEntry?.arguments?.getString(ForecastWholeDayDestination.cityNameArg)

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
                cityName?.let { viewModel.getWholeDayWeather(it) }
                MainScope().launch {
                    isRefreshing = false
                }
            },
        ) {
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(color = primaryColor),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                when (wholeDayWeatherUiState) {
                    is WholeDayForecastUiState.Success -> {
                        val weatherList =
                            (wholeDayWeatherUiState as WholeDayForecastUiState.Success).data.list
                        WholeDayGroupedWeatherList(
                            weatherList = weatherList, isCelsius = isCelsius
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

                    else -> {
                        Text(text = stringResource(R.string.something_went_wrong))
                    }
                }
            }
        }
    }
}

@Composable
fun HourlyForecastListItem(
    weatherList: ListElement, isCelsius: Boolean
) {
    val temperatureMainText = HomeTemperatureConverter(
        isCelsius = isCelsius, temperature = weatherList.main.temp ?: 0.0
    )
    val temperatureFeelLikesText = HomeTemperatureConverter(
        isCelsius = isCelsius, temperature = weatherList.main.feelsLike ?: 0.0
    )


    Surface(
        shape = RoundedCornerShape(8.dp),
        color = secondaryColor,
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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = weatherList.dt.toTimeString(),
                            style = Typography.bodyLarge
                        )
                        WeatherIcon(icon = weatherList.weather[0].icon.toString())
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = weatherList.weather[0].description.toString(),
                        style = Typography.bodyMedium,
                    )
                }
                Text(
                    text = temperatureMainText,
                    style = Typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            ForecastDetailTextRowContent(
                title = stringResource(R.string.weather_feels_like),
                detail = temperatureFeelLikesText
            )
            HorizontalDivider()
            ForecastDetailTextRowContent(
                title = stringResource(R.string.weather_wind_speed),
                detail = "${weatherList.wind.speed} ${stringResource(R.string.kilo_per_hour)}"
            )
            HorizontalDivider()
            ForecastDetailTextRowContent(
                title = stringResource(R.string.weather_humidity),
                detail = "${weatherList.main.humidity}${stringResource(R.string.percentage)}"
            )
        }
    }
}


