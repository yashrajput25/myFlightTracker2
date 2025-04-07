package com.example.myflighttracker2

import FlightRecord

interface FlightDAO{

    suspend fun insertFlight()

    suspend fun getAll(): List<FlightRecord>

    suspend fun getAverageTimePerFlight(): List<AvgTime>

}