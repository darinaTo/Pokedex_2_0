package com.example.pokedex_2_0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pokedex_2_0.ui.pokemondetail.PokemonDetailScreen
import com.example.pokedex_2_0.ui.pokemonlist.PokemonListScreen
import com.example.pokedex_2_0.ui.theme.Pokedex_2_0Theme
import com.example.pokedex_2_0.util.Constants.POKEMON_DETAIL_ROUTE
import com.example.pokedex_2_0.util.Constants.POKEMON_LIST_ROUTE
import com.example.pokedex_2_0.util.Constants.pokemonDetailArguments
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val work = OneTimeWorkRequestBuilder<PokedexWorkManager>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED
                    )
                    .build()
            ).setInitialDelay(2, TimeUnit.SECONDS)
            .build()

        val workManager = WorkManager.getInstance(applicationContext)

        workManager.beginUniqueWork(
            "pokedex",
                ExistingWorkPolicy.KEEP,
            work
            )
            .enqueue()

        setContent {
            Pokedex_2_0Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = POKEMON_LIST_ROUTE) {
                    composable(POKEMON_LIST_ROUTE) {
                        PokemonListScreen(onScreenTab = { route ->
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
