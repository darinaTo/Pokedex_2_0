package com.example.pokedex_2_0.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonInfoFullDbEntity(
    @Embedded val pokemonEntity : PokemonInfoDBEntity,
    @Relation(parentColumn = "id", entityColumn = "pokemonId")
    val statList: List<StatEntity>,
    @Relation(parentColumn = "id", entityColumn = "pokemonId")
    val typeList: List<TypeEntity>,
)
