package com.example.traveldiary.ui.screens.addtravel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traveldiary.data.repositories.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddTravelState(
    val destination: String = "",
    val date: String = "",
    val description: String = ""
)

interface AddTravelActions {
    fun setDestination(destination: String)
    fun setDate(date: String)
    fun setDescription(description: String)
}

class AddTravelViewModel(
    val repository: SettingsRepository
) :  ViewModel() {
    private val _state = MutableStateFlow(AddTravelState())
    private val state = _state.asStateFlow()

    val actions = object : AddTravelActions {
        override fun setDestination(destination: String) = viewModelScope.launch {
            repository
        }

        override fun setDate(date: String) =
            _state.update{ it.copy(date = date) }

        override fun setDescription(description: String) =
            _state.update{ it.copy(description = description) }
    }
}