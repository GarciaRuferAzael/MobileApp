package com.example.traveldiary.data.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//query
@Dao
interface TripsDAO{
    @Query("SELECT * FROM trip")
    fun getAll(): Flow<List<Trip>>

    @Query("SELECT * FROM trip WHERE id=:id")
    fun getById(id: Int): Trip

    suspend fun upsert(trip: Trip)

    suspend fun delete(trip: Trip)
}