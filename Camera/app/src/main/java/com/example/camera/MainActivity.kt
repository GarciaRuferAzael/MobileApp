package com.example.camera

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.camera.ui.theme.CameraTheme
import com.example.camera.utils.rememberCameraLauncher
import com.example.camera.utils.saveImageToStorage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CameraTheme {
                CameraScreen()
            }
        }
    }
}

@Composable
fun CameraScreen() {
    val ctx = LocalContext.current
    val cameraLauncher = rememberCameraLauncher(
        onPictureTaken = { imageUri -> saveImageToStorage(imageUri, ctx.contentResolver) }
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Button(onClick = cameraLauncher::captureImage) {
                    Text("Take a Picture")
                }
            }

            if (cameraLauncher.capturedImageUri != Uri.EMPTY) {
                AsyncImage(
                    ImageRequest.Builder(ctx)
                        .data(cameraLauncher.capturedImageUri)
                        .crossfade(true)
                        .build(),
                    "Captured Image"
                )
            }
        }
    }
}
