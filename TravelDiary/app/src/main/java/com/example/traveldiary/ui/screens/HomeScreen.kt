package com.example.traveldiary.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.traveldiary.ui.theme.AppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val items = (1..20).map { "Item $it" }
}

@Composable
fun TravelItem(item: String){
    Card(
        onClick = {}
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {

        }
    }
}