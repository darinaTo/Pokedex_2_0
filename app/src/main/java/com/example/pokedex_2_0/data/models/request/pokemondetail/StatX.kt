package com.example.pokedex_2_0.data.models.request.pokemondetail

import com.squareup.moshi.Json

data class StatX(
    @Json(name = "name") val name: String,
)