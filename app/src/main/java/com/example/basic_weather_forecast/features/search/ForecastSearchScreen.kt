package com.example.basic_weather_forecast.features.search

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.common.ui.SearchBox
import com.example.basic_weather_forecast.navigation.NavigationDestination
import com.example.basic_weather_forecast.features.home.presentation.HomeViewModel
import com.example.basic_weather_forecast.features.whole_day.presentation.WholeDayViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

object ForecastSearchDestination : NavigationDestination {
    override val route: String
        get() = "search"
    override val titleRes: Int
        get() = R.string.search_screen

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastSearchScreen(
    navigateBack: () -> Unit, modifier: Modifier = Modifier,
    mainViewModel: HomeViewModel = hiltViewModel(),
    wholeDayViewModel: WholeDayViewModel = hiltViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SearchBox(
                            searchText = searchQuery,
                            onSearchTextChanged = { searchQuery = it },
                            onSearchSubmit = {
                                MainScope().launch {
                                    mainViewModel.getCurrentWeather(searchQuery)
                                    wholeDayViewModel.getWholeDayWeather(searchQuery)
                                }
                            },
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            val context = LocalContext.current
            val countries = remember { getStringArrayResource(context, R.array.countries_array) }

            val filteredCountries = countries.filter {
                it.contains(searchQuery, ignoreCase = true)
            }
            CountryListBuilder(filteredCountries)
        }
    }
}

fun getStringArrayResource(context: Context, resourceId: Int): List<String> {
    return context.resources.getStringArray(resourceId).toList()
}

@Composable
fun CountryListBuilder(
    countries: List<String>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(countries) { country ->
            CountryListItem(country)
        }
    }
}

@Composable
fun CountryListItem(country: String) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .clickable { },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = country,
            fontSize = 18.sp,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}
