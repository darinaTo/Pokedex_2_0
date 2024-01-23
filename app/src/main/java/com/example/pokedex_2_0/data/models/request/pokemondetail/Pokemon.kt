package com.example.pokedex_2_0.data.models.request.pokemondetail

data class Pokemon(
    val base_experience: Int,
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)