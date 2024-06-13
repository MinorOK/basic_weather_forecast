package com.example.basic_weather_forecast

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.basic_weather_forecast.common.theme.primaryColor
import com.example.basic_weather_forecast.common.theme.secondaryColor
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
    titleTextAlign: TextAlign? = TextAlign.Start,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    canNavigateBack: Boolean,
    canActionButton: Boolean = false,
    navigateUp: () -> Unit = {},
    navigateBackIcon: ImageVector? = null,
    actionIcon: ImageVector? = null,
    onActionPressed: () -> Unit = {},
) {
    val backgroundColor = primaryColor

    TopAppBar(
        title = {
            Text(
                title ?: "",
                color = secondaryColor,
                textAlign = titleTextAlign,
                modifier = Modifier.fillMaxWidth()
            )
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
                        tint = secondaryColor
                    )
                }
            }
        },
        actions = {
            if (canActionButton)
                IconButton(onClick = onActionPressed) {
                    Icon(
                        imageVector = actionIcon ?: Icons.Default.Search,
                        contentDescription = stringResource(R.string.action_button),
                        tint = secondaryColor
                    )
                }
        }
    )
}