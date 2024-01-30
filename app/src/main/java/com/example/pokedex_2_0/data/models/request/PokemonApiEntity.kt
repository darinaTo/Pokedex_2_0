package com.example.pokedex_2_0.data.models.request

import com.squareup.moshi.Json


data class PokemonApiEntity(
   @Json(name = "name") val name: String,
   @Json(name = "url") val url: String,
)