package com.example.traveldiary

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.traveldiary.data.database.TravelDiaryDatabase
import com.example.traveldiary.data.repositories.TripsRepository
import com.example.traveldiary.data.repositories.SettingsRepository
import com.example.traveldiary.ui.TripsViewModel
import com.example.traveldiary.ui.screens.addtravel.AddTravelViewModel
import com.example.traveldiary.ui.screens.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val Context.dataStore by preferencesDataStore("settings")

val appModule = module {
    single { get<Context>().dataStore }

    single {
        Room.databaseBuilder(
            get(),
            TravelDiaryDatabase::class.java,
            "travel-diary"
        ).build()
    }

    single { SettingsRepository(get()) }

    single { TripsRepository(get<TravelDiaryDatabase>().tripsDAO()) }

    viewModel { AddTravelViewModel() }

    viewModel { SettingsViewModel(get()) }

    viewModel { TripsViewModel(get()) }
}
