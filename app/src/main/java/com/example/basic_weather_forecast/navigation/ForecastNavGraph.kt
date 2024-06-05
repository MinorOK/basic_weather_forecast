package com.example.basic_weather_forecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.basic_weather_forecast.ui.main.ForecastMainDestination
import com.example.basic_weather_forecast.ui.main.ForecastMainScreen
import com.example.basic_weather_forecast.ui.search.ForecastSearchDestination
import com.example.basic_weather_forecast.ui.search.ForecastSearchScreen
import com.example.basic_weather_forecast.ui.settings.ForecastSettingsDestination
import com.example.basic_weather_forecast.ui.settings.ForecastSettingsScreen
import com.example.basic_weather_forecast.ui.whole_day.ForecastWholeDayDestination
import com.example.basic_weather_forecast.ui.whole_day.ForecastWholeDayScreen

@Composable
fun ForecastNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ForecastMainDestination.route,
        modifier = modifier
    ) {
        composable(route = ForecastMainDestination.route) {
            ForecastMainScreen(
                navigateToSettingsScreen = { navController.navigate(ForecastSettingsDestination.route) },
                navigateToWholeDayScreen = { cityName ->
                    navController.navigate(ForecastWholeDayDestination.createRoute(cityName))
                },
                navigateToSearchScreen = { navController.navigate(ForecastSearchDestination.route) }
            )
        }
        composable(
            route = ForecastWholeDayDestination.route,
            arguments = listOf(navArgument(ForecastWholeDayDestination.cityNameArg) {
                type = NavType.StringType
            })
        ) {
            ForecastWholeDayScreen(
                navigateBack = {
                    navController.navigateUp()
                },
            )
        }
        composable(
            route = ForecastSearchDestination.route,
        ) {
            ForecastSearchScreen(
                navigateBack = {
                    navController.navigateUp()
                },
            )
        }
        composable(
            route = ForecastSettingsDestination.route,
        ) {
            ForecastSettingsScreen(navigateBack = {
                navController.navigateUp()
            })
        }
    }
}