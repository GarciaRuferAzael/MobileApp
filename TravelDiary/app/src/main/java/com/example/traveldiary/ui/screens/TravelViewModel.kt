package com.example.traveldiary.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveldiary.data.database.Trip
import com.example.traveldiary.data.repositories.TravelRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TravelViewModel {
    data class TodosState(val todos: List<Trip>)

    interface TravelActions {
        fun addTrip(trip: Trip): Job
        fun removeTrip(trip: Trip): Job
    }

    class TravelViewModel(private val repository: TravelRepository) : ViewModel() {
        val state = repository.trips.map { TodosState(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = TodosState(emptyList())
        )
        val actions = object : TravelActions {
            override fun addTodo(todo: Todo) = viewModelScope.launch {
                repository.upsert(todo)
            }
            override fun removeTodo(todo: Todo) = viewModelScope.launch {
                repository.delete(todo)
            }
            override fun toggleComplete(todo: Todo) = viewModelScope.launch {
                repository.upsert(todo.copy(isComplete = !todo.isComplete))
            }
        }
    }
}