package com.example.activitylifecycle

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.activitylifecycle.ui.theme.ActivityLifecycleTheme
import com.example.activitylifecycle.ui.theme.Purple80
import java.time.format.TextStyle

private const val TAG = "MainActivity"
private const val TAG2 = "composable"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
        enableEdgeToEdge()
        setContent {
            ActivityLifecycleTheme {
                Scaffold(
                    // containerColor = Purple80,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val config = LocalConfiguration.current

                    Surface (
                        color = Purple80,
                        modifier = Modifier
                            .padding(innerPadding)
                            .size(
                                config.screenWidthDp.dp / 2,
                                config.screenHeightDp.dp
                            )
                    ){
                        /*Greeting(
                            name = "Android",
                        )*/
                        // Orientation(config)
                        // TextStyle()
                        // HorizontalLayout()
                        // VerticalLayout()
                        // ConstraintLayout()
                        // DynamicContent(listOf("1", "2", "3", "4", "5", "6"))
                        // SlowScrollableContent((0..10000).map { "Elem $it" })
                        FastScrollableContent((0..10000).map { "Elem $it" })
                    }

                }
            }
        }
    }

    /*override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart")
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }
     */
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        Log.i(TAG2, "onCreate")
    }
    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        Log.i(TAG2, "onStart")
    }
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        Log.i(TAG2, "onResume")
    }
    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        Log.i(TAG2, "onPause")
    }
    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        Log.i(TAG2, "onStop")
    }
    /*LifecycleEventEffect(Lifecycle.Event.ON_DESTROY) {
        Log.i(TAG2, "onDestroy")
    }*/
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActivityLifecycleTheme {
        Greeting("Android")
    }
}

@Composable
fun Orientation(config: Configuration) {
    Text(
        when(config.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> "Portrait"
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
            else -> "Unknown"
        }
    )
}

@Composable
fun TextStyle() {
    Text(
        "Hello, World!",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun HorizontalLayout() {
    Row {
        Text("First")
        Text("Second")
        Text("Third")
    }
}

@Composable
fun VerticalLayout() {
    Column {
        Text("First")
        Text("Second")
        Text("Third")
    }
}

@Composable
fun ConstraintLayout() {
    BoxWithConstraints {
        val box = this // equivalente di this@BoxWithConstraints

        Column {
            if (this@BoxWithConstraints.maxHeight >= 400.dp) {
                Text(">= 400.dp")
            }
            Text("""
            minHeight: ${box.minHeight}
            maxHeight: ${box.maxHeight}
            minWidth: ${box.minWidth}
            maxWidth: ${box.maxWidth}
        """.trimIndent())
        }
    }
}

@Composable
fun DynamicContent(items: List<String>) {
    Column {
        for (item in items) {
            Text(item)
        }
    }
}

@Composable
fun SlowScrollableContent(items: List<String>) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        for (item in items) {
            Text(item)
        }
    }
}

@Composable
fun FastScrollableContent(elems: List<String>) {
    LazyColumn {
        items(elems) {
            Text(it)
        }
    }
}