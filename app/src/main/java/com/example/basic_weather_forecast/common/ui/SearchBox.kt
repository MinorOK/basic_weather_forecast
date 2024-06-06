package com.example.basic_weather_forecast.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchSubmit: () -> Unit,
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Search...")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done // Change to Done action
        ),
        keyboardActions = KeyboardActions(onDone = { onSearchSubmit() }) // Call onSearchSubmit when Enter is pressed
    )
}