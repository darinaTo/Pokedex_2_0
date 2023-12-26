package com.example.pokedex_2_0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pokedex_2_0.ui.theme.Pokedex_2_0Theme

class PokemonNavigation : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pokedex_2_0Theme {
 /*               val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemon_list") {
                    composable("pokemon_list/") {
                        PokemonListScreen(navController = navController)
                    }
                    composable("pokemon_detail/{pokemonColor}/{pokemonName}",
                        arguments = listOf(
                            navArgument("pokemonColor") {
                                type = NavType.IntType
                            },
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val pokemonColor = remember {
                            val color = it.arguments?.getInt("pokemonColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                    }

                }*/
            }
        }
    }
}
