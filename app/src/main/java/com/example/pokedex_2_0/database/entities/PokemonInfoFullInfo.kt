package com.example.pokedex_2_0.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonInfoFullInfo(
    @Embedded val pokemonEntity : PokemonInfoDBEntity,
    @Relation(parentColumn = "id", entityColumn = "pokemonId")
    val statEntities: List<StatEntity>,
    @Relation(parentColumn = "id", entityColumn = "pokemonId")
    val typeEntities: List<TypeEntity>,
)
