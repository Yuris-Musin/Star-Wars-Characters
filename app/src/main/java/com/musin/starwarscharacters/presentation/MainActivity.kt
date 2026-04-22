package com.musin.starwarscharacters.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.musin.starwarscharacters.presentation.screen.CharacterDetailScreen
import com.musin.starwarscharacters.presentation.screen.CharactersScreen
import com.musin.starwarscharacters.presentation.ui.theme.StarWarsCharactersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StarWarsCharactersTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "characters") {
                    composable("characters") {
                        CharactersScreen(navController = navController)
                    }
                    composable(
                        route = "character/{characterId}",
                        arguments = listOf(androidx.navigation.navArgument("characterId") {
                            type = androidx.navigation.NavType.IntType
                        })
                    ) { backStackEntry ->
                        val characterId = backStackEntry.arguments?.getInt("characterId") ?: return@composable
                        CharacterDetailScreen(
                            characterId = characterId,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}