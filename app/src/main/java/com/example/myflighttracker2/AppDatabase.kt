package com.example.myflighttracker2


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities =[FlightRecord::class], version = 1)

abstract class AppDatabase: RoomDatabase(){
    abstract fun flightDao(): FlightDAO
}