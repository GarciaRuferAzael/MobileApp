package com.example.traveldiary.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveldiary.data.database.Trip
import com.example.traveldiary.data.repositories.TripsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TodosState(val todos: List<Trip>)

interface TravelActions {
    fun addTrip(trip: Trip): Job
    fun removeTrip(trip: Trip): Job
}

class TripsViewModel(private val repository: TripsRepository) : ViewModel() {
    val state = repository.trips.map { TodosState(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TodosState(emptyList())
    )
    val actions = object : TravelActions {
        override fun addTrip(trip: Trip) = viewModelScope.launch {
            repository.upsert(trip)
        }

        override fun removeTrip(trip: Trip) = viewModelScope.launch {
            repository.delete(trip)
        }
    }
}
