package com.example.basic_weather_forecast

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
    titleTextAlign: TextAlign? = TextAlign.Start,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    canNavigateBack: Boolean,
    canActionButton: Boolean = false,
    navigateUp: () -> Unit = {},
    navigateBackIcon: ImageVector? = null,
    actionIcon: ImageVector? = null,
    onActionPressed: () -> Unit = {},
    openSearch: Boolean = false,
    searchText: String = "",
    onSearchTextChanged: (String) -> Unit = {},
    onSearchSubmit: () -> Unit = {}
) {
    val backgroundColor = Color(0xFF2E335A)
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    TopAppBar(
        title = {
            if (openSearch)
//                SearchBox(
//                    searchText = searchText,
//                    onSearchTextChanged = onSearchTextChanged,
//                    onSearchSubmit = onSearchSubmit
//                )
                SearchBar(
                    modifier = Modifier
//                        .align(Alignment.TopCenter)
                        .semantics { traversalIndex = 0f },
                    inputField = {
                        SearchBarDefaults.InputField(
                            colors = TextFieldDefaults.colors(),
//                            query = text,
//                            onQueryChange = { text = it },
//                            onSearch = { expanded = false },
                            query = searchText,
                            onQueryChange = onSearchTextChanged,
                            onSearch = {
                                expanded = false
                                onSearchSubmit()
                            },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            placeholder = { Text("ค้นหาสถานที่") },
                            leadingIcon = {
                                if (!expanded)
                                    IconButton(onClick = { }) {
                                        Icon(Icons.Default.Menu, contentDescription = null)
                                    } else
                                    IconButton(onClick = {

                                    }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                            },
                            trailingIcon = {
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = null)
                                }
                            },
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        repeat(4) { idx ->
                            val resultText = "Suggestion $idx"
                            ListItem(headlineContent = { Text(resultText) },
                                supportingContent = { Text("Additional info") },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Star,
                                        contentDescription = null
                                    )
                                },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                modifier = Modifier
                                    .clickable {
                                        text = resultText
                                        expanded = false
                                    }
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp))
                        }
                    }
                }
            else
                Text(
                    title ?: "-",
                    color = Color.White,
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
                        tint = Color.White
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
                        tint = Color.White
                    )
                }
        }
    )
}