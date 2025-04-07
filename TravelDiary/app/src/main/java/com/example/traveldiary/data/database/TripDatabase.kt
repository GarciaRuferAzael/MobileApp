package com.example.traveldiary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Trip::class], version = 1)
abstract class TripDatabase : RoomDatabase() {
    //dichiarazione db
    abstract fun tripsDAO(): TripsDAO
}