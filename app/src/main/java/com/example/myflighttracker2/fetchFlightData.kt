package com.example.myflighttracker2


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
suspend fun fetchFlightData():List<FlightRecord> {

    val flights = listOf("AI101","AI102","AI103", "AI104" )
    val origins = listOf("DEL","BOM")
    val dests = listOf("BLR", "HYD")
    val today = LocalDate.now()

    return (0..6).flatMap {
            dayOffset ->
        val date = today.minusDays(dayOffset.toLong()).toString()
        flights.map {
            FlightRecord(
                flightNumber = it,
                date = date,
                origin = origins.random(),
                destination = dests.random(),
                scheduledTimeMinutes = (120..150).random(),
                delayMinutes = (0..30).random()
            )
        }

    }

}

