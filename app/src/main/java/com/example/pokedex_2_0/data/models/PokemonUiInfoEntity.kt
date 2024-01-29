package com.example.pokedex_2_0.data.models

import com.example.pokedex_2_0.data.models.request.pokemondetail.Stat
import com.example.pokedex_2_0.data.models.request.pokemondetail.Type

data class PokemonUiInfoEntity(
    val id : Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<Stat>,
    val types: List<Type>
)
