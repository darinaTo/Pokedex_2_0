package com.example.pokedex_2_0.data.models

import com.example.pokedex_2_0.data.models.request.pokemondetail.Stat
import com.example.pokedex_2_0.data.models.request.pokemondetail.Type

data class PokemonUiInfoEntity(
    val id: Int = -1,
    val name: String = "",
    val height: Float = -1f,
    val weight: Float = -1f,
    val stats: List<Stat> = emptyList(),
    val types: List<Type> = emptyList()
)
