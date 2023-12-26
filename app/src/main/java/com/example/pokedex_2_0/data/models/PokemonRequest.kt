package com.example.pokedex_2_0.data.models

import com.squareup.moshi.Json

data class PokemonRequest(
    val count: Int,
    @Json(name = "next") val next: String,
    @Json(name = "previous") val previous: Any,
    @Json(name = "results") val results: List<Pokemon>
)