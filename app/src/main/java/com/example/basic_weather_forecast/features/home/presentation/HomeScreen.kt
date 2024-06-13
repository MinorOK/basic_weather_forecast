package com.example.basic_weather_forecast.features.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_weather_forecast.ForecastAppBar
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.common.ui.SearchBox
import com.example.basic_weather_forecast.features.home.domain.model.HomeForecastCurrentWeatherUiState
import com.example.basic_weather_forecast.navigation.NavigationDestination
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


object ForecastMainDestination : NavigationDestination {
    override val route: String
        get() = "main"
    override val titleRes: Int
        get() = R.string.main_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastMainScreen(
    navigateToWholeDayScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var cityName by rememberSaveable { mutableStateOf<String?>(null) }
    var isRefreshing by remember { mutableStateOf(false) }
    var isSearchOpen by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val isCelsius = viewModel.isCelsius.collectAsState()
    val currentWeatherUiState by viewModel.currentWeatherUiState.collectAsState()

    LaunchedEffect(viewModel) {
        cityName = viewModel.getSearchString()
        cityName?.let {
            viewModel.getCurrentWeather(it)
        }
    }

    Scaffold(
        topBar = {
            when (currentWeatherUiState) {
                is HomeForecastCurrentWeatherUiState.Success -> {
                    ForecastAppBar(
                        title = (currentWeatherUiState as HomeForecastCurrentWeatherUiState.Success).data.name,
                        titleTextAlign = TextAlign.Center,
                        navigateBackIcon = Icons.Default.Settings,
                        canNavigateBack = true,
                        navigateUp = {
                            isBottomSheetVisible = true
                        },
                        canActionButton = true,
                        actionIcon = if (isSearchOpen) Icons.Default.Clear else null,
                        onActionPressed = {
                            searchText = ""
                            isSearchOpen = !isSearchOpen
                        },
                    )
                }

                else -> {
                    ForecastAppBar(
                        title = null,
                        titleTextAlign = TextAlign.Center,
                        navigateBackIcon = Icons.Default.Settings,
                        canNavigateBack = true,
                        navigateUp = {
                            isBottomSheetVisible = true
                        },
                        canActionButton = true,
                        actionIcon = if (isSearchOpen) Icons.Default.Clear else null,
                        onActionPressed = {
                            searchText = ""
                            isSearchOpen = !isSearchOpen
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    isRefreshing = true
                    cityName?.let { viewModel.getCurrentWeather(it) }
                    MainScope().launch {
                        isRefreshing = false
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFF2E335A))
                        .verticalScroll(rememberScrollState())
                ) {
                    when (currentWeatherUiState) {
                        is HomeForecastCurrentWeatherUiState.Success -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                            ) {
                                cityName?.let {
                                    ForecastMainScreenBody(
                                        (currentWeatherUiState as HomeForecastCurrentWeatherUiState.Success).data,
                                        navigateToWholeDayScreen,
                                        it,
                                        isCelsius
                                    )
                                }
                            }
                        }

                        is HomeForecastCurrentWeatherUiState.Error -> {
                            Text(
                                text = (currentWeatherUiState as HomeForecastCurrentWeatherUiState.Error).message,
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            )
                        }

                        is HomeForecastCurrentWeatherUiState.Loading -> {
                            CircularProgressIndicator()
                        }

                        is HomeForecastCurrentWeatherUiState.Nothing -> {
                            Text(text = stringResource(R.string.weather_data_is_empty))
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = isSearchOpen,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                SearchBox(
                    searchText = searchText,
                    onSearchTextChanged = { searchText = it },
                    onSearchSubmit = {
                        cityName = searchText
                        isSearchOpen = !isSearchOpen
                        viewModel.viewModelScope.launch {
                            viewModel.setSearchString(cityName ?: "")
                            cityName = viewModel.getSearchString()
                            cityName?.let { viewModel.getCurrentWeather(it) }
                        }
                        searchText = ""
                    }
                )
            }

            if (isBottomSheetVisible) {
                ModalBottomSheet(
                    onDismissRequest = { isBottomSheetVisible = false }
                ) {
                    SettingsContent(viewModel)
                }
            }
        }
    }
}

@Composable
fun SettingsContent(
    viewModel: HomeViewModel
) {
    val isCelsius by viewModel.isCelsius.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.temperature),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isCelsius)
                        Text(
                            text = "${stringResource(R.string.weather_celsius)} (${stringResource(R.string.weather_unit_celsius)})",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            ),
                        )
                    else
                        Text(
                            text = "${stringResource(R.string.weather_fahrenheit)} (${
                                stringResource(
                                    R.string.weather_unit_fahrenheit
                                )
                            })",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            ),
                        )
                    Spacer(modifier = Modifier.width(6.dp))
                    Switch(
                        checked = isCelsius,
                        onCheckedChange = {
                            viewModel.setCelsius(it)
                        }
                    )
                }
            }
        }
    }
}