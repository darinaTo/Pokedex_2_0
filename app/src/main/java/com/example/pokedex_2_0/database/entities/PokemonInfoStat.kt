package com.example.pokedex_2_0.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity("pokemon_info_full")
data class PokemonInfoStat (
    @Embedded
    val pokemonInfo: PokemonInfo,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonTypeId"
    )
    val types: List<PokemonType>,

   // val stat: List<PokemonStat>
)
