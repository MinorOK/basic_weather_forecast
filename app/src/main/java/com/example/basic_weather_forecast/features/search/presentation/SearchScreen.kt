package com.example.basic_weather_forecast.features.search.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.basic_weather_forecast.ForecastAppBar
import com.example.basic_weather_forecast.R
import com.example.basic_weather_forecast.navigation.NavigationDestination
import com.example.basic_weather_forecast.features.home.presentation.HomeViewModel
import com.example.basic_weather_forecast.features.whole_day.presentation.WholeDayViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object ForecastSearchDestination : NavigationDestination {
    override val route: String
        get() = "search"
    override val titleRes: Int
        get() = R.string.search_screen

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigateBack: () -> Unit, modifier: Modifier = Modifier,
    mainViewModel: HomeViewModel = hiltViewModel(),
    wholeDayViewModel: WholeDayViewModel = hiltViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var searchQuery by remember { mutableStateOf("") }

    val sheetState = rememberModalBottomSheetState()
    val bottomSheetScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ForecastAppBar(
                title = "จัดการเมืองต่าง ๆ",
                canNavigateBack = true,
                canActionButton = true,
                navigateUp = {
                    navigateBack()
                },
                actionIcon = Icons.Default.Edit,
                onActionPressed = {
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet = true
                }
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "",
                )
            }
        }
    ) { innerPadding ->
        SearchBarSample(innerPadding)
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Top,
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//                .background(color = Color(0xFF2E335A))
//                .verticalScroll(rememberScrollState())
//        ) {
//            val forecastItems = intArrayOf(1)
////            Box(
////                Modifier
////                    .fillMaxSize()
////                    .height(1000.dp)
////                    .background(color = Color(0xFF2E335A)),
////                contentAlignment = Alignment.TopCenter,
////            ) {
////                LazyColumn(
////                    modifier = Modifier.fillMaxSize()
////                ) {
////                    items(forecastItems.size) {
////                        ForecastLocationCard()
////                    }
////                }
////            }
//        }
        if (showBottomSheet) {
            ForecastSearchBottomSheet(
                onShowBottomSheetChanged = { showBottomSheet = it },
                sheetState,
                bottomSheetScope,
                innerPadding,
            )
        }
    }
}

@Composable
private fun ForecastLocationCard() {
    Surface(shape = RoundedCornerShape(16.dp),
        color = Color.White,
        tonalElevation = 4.dp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable {}) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 18.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "กรุงเทพมหานคร",
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "32${stringResource(R.string.weather_unit)}",
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "country code: JP",
                        color = Color.Black,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "เมฆมาก", color = Color.Black, style = TextStyle(
                            fontWeight = FontWeight.Normal,
                        ), modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ForecastSearchBottomSheet(
    onShowBottomSheetChanged: (Boolean) -> Unit = {},
    sheetState: SheetState,
    bottomSheetScope: CoroutineScope,
    innerPadding: PaddingValues
) {
    ModalBottomSheet(
        onDismissRequest = {
            onShowBottomSheetChanged(false)
        },
        sheetState = sheetState,
        containerColor = Color.Black,
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Button(onClick = {
                bottomSheetScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onShowBottomSheetChanged(false)
                    }
                }
            }) {
                Text("Hide bottom sheet")
            }
        }
        Spacer(modifier = Modifier.padding(innerPadding))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSample(innerPadding: PaddingValues) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
            .padding(innerPadding)
            .background(color = Color(0xFF2E335A))
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    colors = TextFieldDefaults.colors(),
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { expanded = false },
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
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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
                        leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
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

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, top = 72.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.semantics { traversalIndex = 1f },
        ) {
            val list = List(100) { "Text $it" }
            items(count = list.size) {
                Text(
                    text = list[it],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
            }
        }
    }
}
