package com.example.pokedex_2_0.data.constants

import androidx.navigation.NavType
import androidx.navigation.navArgument

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val LIMIT = 20
    const val OFFSET = 10
    const val POKEMON_INFO_TABLE = "pokemon_info"
    const val POKEMON_TABLE = "pokemon"
    const val POKEMON_STAT_TABLE = "stat_table"
    const val POKEMON_TYPE_TABLE = "type_table"
    const val POKEMON_LIST_ROUTE = "pokemon_list_screen"
    const val POKEMON_DETAIL_ROUTE = "pokemon_detail_screen"
    const val CHANNEL_ID = "POKEDEX_NOTIFICATION"
    const val NOTIFICATION_ID = 1
    const val ERROR_MESSAGE = "Some trouble with network connection"

    val pokemonDetailArguments = listOf(
            navArgument("pokemonColor") {
                type = NavType.IntType
            },
            navArgument("pokemonName") {
                type = NavType.StringType
            },
            navArgument("pokemonImg") {
                type = NavType.StringType
            },
    )
}