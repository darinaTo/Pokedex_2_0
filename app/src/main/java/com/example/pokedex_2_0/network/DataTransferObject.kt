package com.example.pokedex_2_0.network

import com.example.pokedex_2_0.data.models.Pokemon
import com.example.pokedex_2_0.data.models.request.Result


fun asDatabaseModel(list: List<Result> ): List<Pokemon> {
    return list.map { entry ->
        Pokemon(
            image = entry.url,
            name = entry.name
        )
    }
}
