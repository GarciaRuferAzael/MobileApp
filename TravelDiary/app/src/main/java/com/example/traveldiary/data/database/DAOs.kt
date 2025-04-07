package com.example.traveldiary.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

//query
@Dao
interface TripsDAO{
    @Query("SELECT * FROM trip")
    fun getAll(): Flow<List<Trip>>

    @Upsert
    suspend fun upsert(trip: Trip)

    @Delete
    suspend fun delete(trip: Trip)
}