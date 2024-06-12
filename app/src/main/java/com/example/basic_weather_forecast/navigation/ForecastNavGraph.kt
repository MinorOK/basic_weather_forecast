package com.example.basic_weather_forecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.basic_weather_forecast.features.home.presentation.ForecastMainDestination
import com.example.basic_weather_forecast.features.home.presentation.ForecastMainScreen
import com.example.basic_weather_forecast.features.whole_day.presentation.ForecastWholeDayDestination
import com.example.basic_weather_forecast.features.whole_day.presentation.ForecastWholeDayScreen

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
                navigateToWholeDayScreen = { cityName ->
                    navController.navigate(ForecastWholeDayDestination.createRoute(cityName))
                },
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
    }
}