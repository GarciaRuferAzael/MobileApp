package com.example.traveldiary.data.repositories

import com.example.traveldiary.data.database.Trip
import com.example.traveldiary.data.database.TripsDAO
import kotlinx.coroutines.flow.Flow

class TripsRepository(
    private val dao: TripsDAO
) {
    val trips: Flow<List<Trip>> = dao.getAll()

    suspend fun upsert(trip: Trip) = dao.upsert(trip)

    suspend fun delete(trip: Trip) = dao.delete(trip)
}
