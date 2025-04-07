package com.example.myflighttracker2

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myflighttracker2.ui.theme.MyFlightTracker2Theme
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "flight-db"
        ).build()



        setContent {

            var flightAverages by remember { mutableStateOf(listOf<AvgTime>()) }

            // Fetch average durations on launch
            LaunchedEffect(Unit) {
                flightAverages = db.flightDao().getAverageTimePerFlight()
            }

            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Flight Average Duration", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(10.dp))
                    AverageTimeUI(flightAverages)
                }
            }
        }
        lifecycleScope.launch {
            val fakeFlights = fetchFlightData()
            fakeFlights.forEach {
                db.flightDao().insertFlight(it)
            }
        }

    }
    private fun scheduleBackgroundJob() {
        val request = PeriodicWorkRequestBuilder<FlightDataWorker>(
            1, TimeUnit.DAYS
        ).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "FlightDataFetcher",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }


}

@Composable
fun AverageTimeUI(flightAverages: List<AvgTime>) {
    LazyColumn {
        items(flightAverages) { avg ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Flight Number: ${avg.flightNumber}")
                    Text("Avg Duration: ${"%.2f".format(avg.avgDuration)} minutes")
                }
            }
        }
    }
}


