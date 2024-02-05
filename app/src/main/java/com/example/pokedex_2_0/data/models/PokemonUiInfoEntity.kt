package com.example.pokedex_2_0.data.models

import com.example.pokedex_2_0.data.models.request.pokemondetail.Stat
import com.example.pokedex_2_0.data.models.request.pokemondetail.Type

data class PokemonUiInfoEntity(
    val id: Int = -1,
    val name: String = "",
    val height: Int = -1,
    val weight: Int = -1,
    val stats: List<Stat> = emptyList(),
    val types: List<Type> = emptyList()
)
