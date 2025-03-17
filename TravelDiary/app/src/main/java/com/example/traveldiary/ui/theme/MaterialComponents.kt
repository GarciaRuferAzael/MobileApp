package com.example.traveldiary.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = { Text("TravelDiary")},
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Search, "Search")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Settings, "Settings")
            }
        }

    )
}