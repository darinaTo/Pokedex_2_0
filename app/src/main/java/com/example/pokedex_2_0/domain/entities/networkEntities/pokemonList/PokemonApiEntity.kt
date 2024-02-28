package com.example.pokedex_2_0.domain.entities.networkEntities.pokemonList

import com.squareup.moshi.Json


data class PokemonApiEntity(
   @Json(name = "name") val name: String,
   @Json(name = "url") val url: String,
)