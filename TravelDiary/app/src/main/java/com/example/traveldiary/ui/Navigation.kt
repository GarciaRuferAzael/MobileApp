package com.example.traveldiary.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.traveldiary.ui.screens.addtravel.AddTravelScreen
import com.example.traveldiary.ui.screens.home.HomeScreen
import com.example.traveldiary.ui.screens.settings.SettingsScreen
import com.example.traveldiary.ui.screens.traveldetails.TravelDetailsScreen
import kotlinx.serialization.Serializable

sealed interface TravelDiaryRoute {
    @Serializable data object Home : TravelDiaryRoute
    @Serializable data class TravelDetails(val travelId: String) : TravelDiaryRoute
    @Serializable data object AddTravel : TravelDiaryRoute
    @Serializable data object Settings : TravelDiaryRoute
}

@Composable
fun TravelDiaryNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = TravelDiaryRoute.Home
    ) {
        composable<TravelDiaryRoute.Home> {
            HomeScreen(navController)
        }
        composable<TravelDiaryRoute.TravelDetails> { backStackEntry ->
            val route = backStackEntry.toRoute<TravelDiaryRoute.TravelDetails>()
            TravelDetailsScreen(navController, route.travelId)
        }
        composable<TravelDiaryRoute.AddTravel> {
            AddTravelScreen(navController)
        }
        composable<TravelDiaryRoute.Settings> {
            SettingsScreen(navController)
        }
    }
}
