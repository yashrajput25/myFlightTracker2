package com.example.myflighttracker2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface FlightDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlight(flightRecord: FlightRecord)
    @Query("SELECT * FROM flights")
    suspend fun getAll(): List<FlightRecord>


    @Query("""
        SELECT flightNumber, 
               AVG(scheduledTimeMinutes + delayMinutes) AS avgDuration
        FROM flights 
        GROUP BY flightNumber
    """)
    suspend fun getAverageTimePerFlight(): List<AvgTime>

}