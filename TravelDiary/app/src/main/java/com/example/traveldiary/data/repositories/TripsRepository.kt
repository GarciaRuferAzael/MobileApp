package com.example.traveldiary.data.repositories

import com.example.traveldiary.data.database.Trip
import com.example.traveldiary.data.database.TripsDAO

class TripsRepository(
    private val dao: TripsDAO
) {
    val trips = dao.getAll()

    suspend fun getTrip(trip: Trip): Trip = dao.getById(trip.id)

    suspend fun upsert(trip: Trip) = dao.upsert(trip)

    suspend fun delete(trip: Trip) = dao.delete(trip)
}