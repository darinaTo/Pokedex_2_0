package com.example.pokedex_2_0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex_2_0.pokemondetail.PokemonDetailScreen
import com.example.pokedex_2_0.pokemonlist.PokemonListScreen
import com.example.pokedex_2_0.ui.theme.Pokedex_2_0Theme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pokedex_2_0Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list_screen") {
                    composable("pokemon_list_screen") {
                        PokemonListScreen(navController = navController)
                    }
                    composable("pokemon_detail_screen/{pokemonColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("pokemonColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            },
                           /* navArgument("pokemonImg") {
                                type = NavType.StringType
                            }*/
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("pokemonColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                      /*  val pokemonImg = remember {
                            it.arguments?.getString("pokemonImg")
                        }*/
                        PokemonDetailScreen(dominantColor = dominantColor,
                            pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                            /*pokemonImg = pokemonImg!!,*/
                            navController = navController)
                    }

                }
            }
        }
    }
}
