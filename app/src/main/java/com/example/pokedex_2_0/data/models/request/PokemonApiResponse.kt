package com.example.pokedex_2_0.data.models.request

import com.squareup.moshi.Json
data class PokemonApiResponse(
    @Json(name = "results") val pokemonApiEntities: List<PokemonApiEntity>
)