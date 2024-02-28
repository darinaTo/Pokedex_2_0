package com.example.pokedex_2_0.domain.entities.networkEntities.pokemonList

import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonList.PokemonApiEntity
import com.squareup.moshi.Json
data class PokemonApiResponse(
    @Json(name = "results") val pokemonApiEntities: List<PokemonApiEntity>
)