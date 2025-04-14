package com.example.gps

import android.Manifest
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gps.ui.theme.GPSTheme
import com.example.gps.utils.LocationService
import com.example.gps.utils.PermissionStatus
import com.example.gps.utils.rememberMultiplePermissions
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GPSTheme {
                GPSScreen()
            }
        }
    }
}

@Composable
fun GPSScreen() {
    val ctx = LocalContext.current

    var showLocationDisabledWarning by remember { mutableStateOf(false) }
    var showPermissionDeniedWarning by remember { mutableStateOf(false) }
    var showPermissionPermanentlyDeniedWarning by remember { mutableStateOf(false) }

    val locationService = remember { LocationService(ctx) }
    val coordinates by locationService.coordinates.collectAsStateWithLifecycle()
    val isLoading by locationService.isLoadingLocation.collectAsStateWithLifecycle()

    // Funcion to get the location
    val scope = rememberCoroutineScope()
    fun getCurrentLocation() = scope.launch {
        try {
            locationService.getCurrentLocation()
        } catch (_: IllegalStateException) {
            showLocationDisabledWarning = true
        }
    }

    // Permission handling
    val locationPermissions = rememberMultiplePermissions(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    ) { statuses ->
        when {
            statuses.any { it.value.isGranted } ->
                getCurrentLocation()
            statuses.all { it.value == PermissionStatus.PermanentlyDenied } ->
                showPermissionPermanentlyDeniedWarning = true
            else ->
                showPermissionDeniedWarning = true
        }
    }

    // Function
    fun getLocationOrRequestPermission() {
        if (locationPermissions.statuses.any { it.value.isGranted }) {
            getCurrentLocation()
        } else {
            locationPermissions.launchPermissionRequest()
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState)},
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            Button(onClick = ::getLocationOrRequestPermission) {
                Text("Get Current Location")
            }
            Spacer(Modifier.height(16.dp))
            Text("Latitude: ${coordinates?.latitude ?: "-"}")
            Text("Longitude: ${coordinates?.longitude ?: "-"}")
        }

        if (showLocationDisabledWarning) {
            AlertDialog(
                title = { Text("Location disabled") },
                text = { Text("Location must be enabled to get your coordinates in the app.") },
                    confirmButton = {
                        TextButton(onClick = {
                            locationService.openLocationSettings()
                            showLocationDisabledWarning = false
                        }) {
                            Text("Enable")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showLocationDisabledWarning = false
                        }) {
                            Text("Dismiss")
                        }
                    },
                    onDismissRequest = {
                        showLocationDisabledWarning = false
                    }
            )
        }

        if (showPermissionDeniedWarning) {
            AlertDialog(
                title = { Text("Location denied") },
                text = { Text("Location must be enabled to get your coordinates in the app.") },
                confirmButton = {
                    TextButton(onClick = {
                        locationService.openLocationSettings()
                        showPermissionDeniedWarning = false
                    }) {
                        Text("Enable")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showPermissionDeniedWarning = false
                    }) {
                        Text("Dismiss")
                    }
                },
                onDismissRequest = {
                    showPermissionDeniedWarning = false
                }
            )
        }

        if (showPermissionPermanentlyDeniedWarning) {
            LaunchedEffect(snackbarHostState) {
                val res = snackbarHostState.showSnackbar(
                    "Location permission is required.",
                    "Go to Settings",
                    duration = SnackbarDuration.Long
                )
                if (res == SnackbarResult.ActionPerformed) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", ctx.packageName, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    if (intent.resolveActivity(ctx.packageManager) != null) {
                        ctx.startActivity(intent)
                    }
                }
                showPermissionPermanentlyDeniedWarning = false
            }
        }
    }
}