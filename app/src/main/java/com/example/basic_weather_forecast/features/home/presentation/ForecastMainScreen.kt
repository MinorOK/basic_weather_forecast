package com.example.basic_weather_forecast.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.features.home.domain.model.ForecastCurrentWeatherUiState
import com.example.basic_weather_forecast.navigation.NavigationDestination
import com.example.basic_weather_forecast.ForecastAppBar
import com.example.basic_weather_forecast.features.settings.presentation.SearchStringViewModel
import com.example.basic_weather_forecast.features.settings.presentation.SettingsPreferencesViewModel
import androidx.lifecycle.viewModelScope
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
    navigateToSettingsScreen: () -> Unit,
    navigateToWholeDayScreen: (String) -> Unit,
    navigateToSearchScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForecastMainViewModel = hiltViewModel(),
    settingsViewModel: SettingsPreferencesViewModel = hiltViewModel(),
    searchViewModel: SearchStringViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val currentWeatherUiState by viewModel.currentWeatherUiState.collectAsState()
    val isCelsius = settingsViewModel.isCelsius.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var openSearch by remember { mutableStateOf(false) }
    var cityName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(searchViewModel) {
        cityName = searchViewModel.getSearchString()
        viewModel.getCurrentWeather(cityName ?: "")
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (currentWeatherUiState) {
                is ForecastCurrentWeatherUiState.Success -> {
                    ForecastAppBar(
                        title = (currentWeatherUiState as ForecastCurrentWeatherUiState.Success).data.name,
                        navigateBackIcon = Icons.Default.Settings,
                        canNavigateBack = true,
                        navigateUp = { navigateToSettingsScreen() },
                        canActionButton = true,
                        onActionPressed = {
                            openSearch = !openSearch
                        },
                        openSearch = openSearch,
                        searchText = searchText,
                        onSearchTextChanged = { newText ->
                            searchText = newText
                        },
                        onSearchSubmit = {
                            openSearch = !openSearch
                            searchViewModel.viewModelScope.launch {
                                searchViewModel.setSearchString(searchText)
                                val newCityName = searchViewModel.getSearchString() ?: ""
                                viewModel.getCurrentWeather(newCityName)
                            }
                            searchText = ""
                        }
                    )
                }

                is ForecastCurrentWeatherUiState.Loading -> {
                    ForecastAppBar(
                        title = null,
                        navigateBackIcon = Icons.Default.Settings,
                        canNavigateBack = true,
                        canActionButton = true,
                        onActionPressed = {
                            openSearch = !openSearch
                        },
                        onSearchTextChanged = { newText ->
                            searchText = newText
                        }
                    )
                }

                else -> {
                    ForecastAppBar(
                        title = null,
                        navigateBackIcon = Icons.Default.Settings,
                        canNavigateBack = true,
                        canActionButton = true,
                        onActionPressed = {
                            openSearch = !openSearch
                        },
                        onSearchTextChanged = { newText ->
                            searchText = newText // Update searchText when the text changes
                        }
//                        onActionPressed = navigateToSearchScreen,
                    )
                }
            }
        },
    ) { innerPadding ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                isRefreshing = true
                viewModel.getCurrentWeather(cityName ?: "")
                MainScope().launch {
                    isRefreshing = false
                }
            },
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(color = Color(0xFF2E335A))
                    .verticalScroll(rememberScrollState())
            ) {
                when (currentWeatherUiState) {
                    is ForecastCurrentWeatherUiState.Success -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                        ) {
                            ForecastMainScreenBody(
                                (currentWeatherUiState as ForecastCurrentWeatherUiState.Success).data,
                                navigateToWholeDayScreen,
                                cityName ?: "",
                                isCelsius
                            )
                        }
                    }

                    is ForecastCurrentWeatherUiState.Error -> {
                        Text(
                            text = (currentWeatherUiState as ForecastCurrentWeatherUiState.Error).message,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        )
                    }

                    is ForecastCurrentWeatherUiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is ForecastCurrentWeatherUiState.Nothing -> {
                        Text(text = stringResource(R.string.weather_data_is_empty))
                    }

                }
            }
        }
    }
}