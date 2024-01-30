package com.example.pokedex_2_0.data.models.request

import com.squareup.moshi.Json

//TODO: You may add @Json(your_filed_name) annotation to rest fields for safe renaming etc
data class PokemonApiResponse(
    @Json(name = "results") val pokemonApiEntities: List<PokemonApiEntity>
)