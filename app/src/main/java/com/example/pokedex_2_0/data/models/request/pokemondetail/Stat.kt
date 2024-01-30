package com.example.pokedex_2_0.data.models.request.pokemondetail

import androidx.room.Embedded
import com.squareup.moshi.Json

data class Stat(
    @Json(name = "base_stat") val base_stat: Int, //todo now this field may be renamed using camelCase :)
    @Embedded
    @Json(name = "stat") val statInfo: StatX //todo StatX is not very clear naming. Please consider renaming
)
