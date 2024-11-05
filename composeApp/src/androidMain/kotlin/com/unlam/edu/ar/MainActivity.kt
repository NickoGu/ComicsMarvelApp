package com.unlam.edu.ar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
//import com.unlam.edu.ar.ui.screens.ComicDetailsScreen

//import com.unlam.edu.ar.ui.screens.SuperheroDetailsScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            Scaffold(
                content = { paddingValue ->
                    MainScreen( paddingValue)
                }
            )


        }
    }
}

@Composable
fun MainScreen( paddingValue: PaddingValues) {
    // Controller es el elemento que nos permite navegar entre pantallas. Tiene las acciones
    // para navegar como naviegate y también la información de en dónde se "encuentra" el usuario
    // a través del back stack
    val controller = rememberNavController()
    Scaffold(
        bottomBar = {  },

        ) { paddingValue ->
        NavHost(navController = controller, startDestination = "home") {
            composable("home") {
                HomeScreen(paddingValue, controller)
            }
            composable("comic_details/{comicId}", arguments = listOf(navArgument("comicId") { type = NavType.IntType})) { backStackEntry ->
                val comicId = backStackEntry.arguments?.getInt("comicId")

                ComicDetailsScreen(paddingValue, controller, comicId)

            }
            composable("superhero_details/{superheroId}", arguments = listOf(navArgument("superheroId") { type = NavType.IntType})) { backStackEntry ->
                val superheroId = backStackEntry.arguments?.getInt("superheroId")
                SuperheroDetailsScreen(paddingValue, controller, superheroId)
            }

        }
    }
}
