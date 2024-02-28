package com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail

import androidx.room.Embedded
import com.squareup.moshi.Json

data class Stat(
    @Json(name = "base_stat") val base_stat: Int,
    @Embedded
    @Json(name = "stat") val statInfo: StatX
)
