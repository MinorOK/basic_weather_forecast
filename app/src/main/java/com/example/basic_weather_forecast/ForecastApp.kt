package com.example.basic_weather_forecast

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.basic_weather_forecast.common.ui.SearchBox
import com.example.basic_weather_forecast.navigation.ForecastNavHost

@Composable
fun ForecastApp(navController: NavHostController = rememberNavController()) {
    ForecastNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastAppBar(
    title: String?,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    canNavigateBack: Boolean,
    canActionButton: Boolean = false,
    navigateUp: () -> Unit = {},
    navigateBackIcon: ImageVector? = null,
    onActionPressed: () -> Unit = {},
    openSearch: Boolean = false,
    searchText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onSearchSubmit: () -> Unit = {}
) {
    val backgroundColor = Color(0xFF2E335A)

    CenterAlignedTopAppBar(
        title = {
            if (openSearch)
                SearchBox(
                    searchText = searchText,
                    onSearchTextChanged = onSearchTextChanged,
                    onSearchSubmit = onSearchSubmit
                )
            else
                Text(title ?: "-", color = Color.White)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        ),
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = navigateBackIcon ?: Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (canActionButton)
                IconButton(onClick = onActionPressed) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.action_button),
                        tint = Color.White
                    )
                }
        }
    )
}