package com.example.pokedex_2_0.data.models.request

data class PokemonRequest(
    val count: Int,
    val next: String,
    val previous: Any?,
    val results: List<Result>
)