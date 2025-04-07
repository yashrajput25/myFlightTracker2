package com.example.myflighttracker2

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myflighttracker2.ui.theme.MyFlightTracker2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlightTrackerApp();
        }
    }
}

@Composable
fun FlightTrackerApp() {
    var flightNumber by remember { mutableStateOf("") }
    var showWebView by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Enter Flight Number:", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = flightNumber,
            onValueChange = { flightNumber = it },
            label = { Text("e.g., AI101") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showWebView = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Track Flight")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (showWebView && flightNumber.isNotBlank()) {
            val query = "https://www.google.com/search?q=flight+$flightNumber+status"
            FlightWebView(url = query)
        }
    }

}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FlightWebView(url: String) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}
