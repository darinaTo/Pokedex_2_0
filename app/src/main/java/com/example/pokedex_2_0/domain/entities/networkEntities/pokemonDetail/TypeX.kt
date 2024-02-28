package com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail

import com.squareup.moshi.Json

data class TypeX(
    @Json(name = "name") val name: String,
)