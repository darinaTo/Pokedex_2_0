package com.example.pokedex_2_0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex_2_0.ui.activities.PokemonDetailScreen
import com.example.pokedex_2_0.ui.activities.PokemonListScreen
import com.example.pokedex_2_0.ui.theme.Pokedex_2_0Theme
import com.example.pokedex_2_0.data.constants.Constants.POKEMON_DETAIL_ROUTE
import com.example.pokedex_2_0.data.constants.Constants.POKEMON_LIST_ROUTE
import com.example.pokedex_2_0.data.constants.Constants.pokemonDetailArguments
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pokedex_2_0Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = POKEMON_LIST_ROUTE) {
                    composable(POKEMON_LIST_ROUTE) {
                        PokemonListScreen(onScreenTap = { route ->
                            navController.navigate(route)
                        })
                    }
                    composable(
                        "${POKEMON_DETAIL_ROUTE}/{pokemonColor}/{pokemonName}/{pokemonImg}",
                        arguments = pokemonDetailArguments
                    ) {
                        PokemonDetailScreen(
                            onArrowBackClick = { navController.popBackStack() })
                    }

                }
            }

        }
    }
}
