package com.example.traveldiary.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.traveldiary.ui.screens.AddTravelScreen
import com.example.traveldiary.ui.screens.HomeScreen
import com.example.traveldiary.ui.screens.SettingsScreen
import com.example.traveldiary.ui.screens.TravelDetailsScreen
import kotlinx.serialization.Serializable

sealed interface TravelDiaryRoute {

    @Serializable
    data object Home

    @Serializable
    data class TravelDetails(val travelId: String) : TravelDiaryRoute

    @Serializable
    data object AddTravel

    @Serializable
    data object Settings
}

@Composable
fun TravelDiaryNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TravelDiaryRoute.Home,
        modifier = modifier
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
