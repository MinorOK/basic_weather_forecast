package com.example.basic_weather_forecast.features.home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_weather_forecast.ForecastAppBar
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.features.home.domain.model.HomeForecastCurrentWeatherUiState
import com.example.basic_weather_forecast.features.settings.presentation.SearchStringViewModel
import com.example.basic_weather_forecast.features.settings.presentation.SettingsPreferencesViewModel
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
    navigateToSettingsScreen: () -> Unit,
    navigateToWholeDayScreen: (String) -> Unit,
    navigateToSearchScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    settingsViewModel: SettingsPreferencesViewModel = hiltViewModel(),
    searchViewModel: SearchStringViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val currentWeatherUiState by viewModel.currentWeatherUiState.collectAsState()
    val isCelsius = settingsViewModel.isCelsius.collectAsState()
    var cityName by rememberSaveable { mutableStateOf<String?>(null) }
    var isSearchOpen by remember { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val suggestionList = stringArrayResource(R.array.countries_array)

    LaunchedEffect(searchViewModel) {
        cityName = searchViewModel.getSearchString()
        cityName?.let {
            viewModel.getCurrentWeather(it)
            viewModel.getGeocode(it, "5")
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            when (currentWeatherUiState) {
                is HomeForecastCurrentWeatherUiState.Success -> {
                    ForecastAppBar(
                        title = (currentWeatherUiState as HomeForecastCurrentWeatherUiState.Success).data.name,
                        titleTextAlign = TextAlign.Center,
                        navigateBackIcon = Icons.Default.Settings,
                        canNavigateBack = true,
                        navigateUp = { navigateToSettingsScreen() },
                        canActionButton = true,
                        actionIcon = if (isSearchOpen) Icons.Default.Clear else null,
                        onActionPressed = {
                            isSearchOpen = !isSearchOpen
                        },
                    )
                }

                is HomeForecastCurrentWeatherUiState.Loading -> {
                    ForecastAppBar(
                        title = null,
                        titleTextAlign = TextAlign.Center,
                        navigateBackIcon = Icons.Default.Settings,
                        canNavigateBack = true,
                        navigateUp = { navigateToSettingsScreen() },
                        canActionButton = true,
                        onActionPressed = {
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
                        navigateUp = { navigateToSettingsScreen() },
                        canActionButton = true,
                        onActionPressed = {
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
                    viewModel.getCurrentWeather(cityName ?: "")
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
                                ForecastMainScreenBody(
                                    (currentWeatherUiState as HomeForecastCurrentWeatherUiState.Success).data,
                                    navigateToWholeDayScreen,
                                    cityName ?: "",
                                    isCelsius
                                )
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
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .semantics { isTraversalGroup = true }
                        .background(color = Color(0xFF2E335A))
                ) {
                    SearchBar(
                        modifier = Modifier.align(Alignment.TopCenter),
                        inputField = {
                            SearchBarDefaults.InputField(
                                colors = TextFieldDefaults.colors(),
                                query = searchText,
                                onQueryChange = { searchText = it },
                                onSearch = {
                                    cityName = it
                                    expanded = false
                                    isSearchOpen = !isSearchOpen
                                    searchViewModel.viewModelScope.launch {
                                        searchViewModel.setSearchString(cityName ?: "")
                                        cityName = searchViewModel.getSearchString() ?: ""
                                        viewModel.getCurrentWeather(cityName ?: "")
                                    }
                                    searchText = ""
                                },
                                expanded = expanded,
                                onExpandedChange = { expanded = it },
                                placeholder = { Text("ค้นหาสถานที่") },
                                leadingIcon = {
                                    if (expanded)
                                        IconButton(onClick = {
                                            expanded = false
                                        }) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = null
                                            )
                                        }
                                },
                                trailingIcon = {
                                    IconButton(onClick = {
                                        cityName = searchText
                                        expanded = false
                                        isSearchOpen = !isSearchOpen
                                        searchViewModel.viewModelScope.launch {
                                            searchViewModel.setSearchString(cityName ?: "")
                                            cityName = searchViewModel.getSearchString() ?: ""
                                            viewModel.getCurrentWeather(cityName ?: "")
                                        }
                                        searchText = ""
                                    }) {
                                        Icon(Icons.Default.Search, contentDescription = null)
                                    }
                                },
                            )
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                    ) {
                        Box(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .fillMaxSize()
                        ) {
                            Column {
                                repeat(suggestionList.size) { idx ->
                                    val resultText = suggestionList[idx]
                                    ListItem(headlineContent = { Text(resultText) },
                                        leadingContent = {
                                            Icon(
                                                Icons.Filled.Star,
                                                contentDescription = null
                                            )
                                        },
                                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                        modifier = Modifier
                                            .clickable {
                                                isSearchOpen = !isSearchOpen
                                                expanded = false
                                                cityName = suggestionList[idx]
                                                searchViewModel.viewModelScope.launch {
                                                    searchViewModel.setSearchString(cityName ?: "")
                                                    cityName =
                                                        searchViewModel.getSearchString() ?: ""
                                                    viewModel.getCurrentWeather(cityName ?: "")
                                                }
                                            }
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}