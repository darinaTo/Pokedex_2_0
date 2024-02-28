package com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail

import com.squareup.moshi.Json

data class PokemonInfoApiResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "height") val height: Int,
    @Json(name = "weight") val weight: Int,
    @Json(name = "stats") val stats: List<Stat>,
    @Json(name = "types") val types: List<Type>
)