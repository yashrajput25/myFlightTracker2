package com.example.myflighttracker2

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class FlightDataWorker(context: Context,workerParameters: WorkerParameters): CoroutineWorker(context, workerParameters) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "flight-db"
        ).build()

        val records = fetchFlightData()
        records.forEach{
            db.flightDao().insertFlight(it)
        }
        return Result.success()
    }

}
