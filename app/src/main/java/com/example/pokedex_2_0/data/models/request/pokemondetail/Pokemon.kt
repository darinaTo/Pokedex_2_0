package com.example.pokedex_2_0.data.models.request.pokemondetail

data class Pokemon(
    val height: Int,
    val id: Int,
    val name: String,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)