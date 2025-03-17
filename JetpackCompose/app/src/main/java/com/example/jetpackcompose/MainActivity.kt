package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeTheme {
                Scaffold (
                    topBar = {
                        AppBar()
                    },
                    floatingActionButton =  {
                        FloatingActionButton(onClick = {}) {
                            Icon(Icons.Filled.Add, "Add item")
                        }
                    }
                ) { contentPadding ->
                    Column(modifier = Modifier.padding(contentPadding)) {
                        //ScrollableList()
                        MaterialList()
                    }
                }
            }
        }
    }
}

@Composable
fun ScrollableList() {
    val elems = (0..100).map { "Elem $it" }

    LazyColumn {
        items(elems) {
            Text(it, modifier = Modifier.fillMaxWidth())
        }
    }
}
