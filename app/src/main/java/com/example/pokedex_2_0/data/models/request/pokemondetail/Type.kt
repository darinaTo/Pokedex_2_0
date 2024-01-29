package com.example.pokedex_2_0.data.models.request.pokemondetail

import androidx.room.Embedded
import com.squareup.moshi.Json

data class Type(
    @Embedded
    @Json(name = "type") val type: TypeX
)