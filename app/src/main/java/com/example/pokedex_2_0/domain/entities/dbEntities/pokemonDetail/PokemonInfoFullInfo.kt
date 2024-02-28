package com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail

import androidx.room.Embedded
import androidx.room.Relation

data class PokemonInfoFullInfo(
    @Embedded val pokemonEntity : PokemonInfoDbEntity,
    @Relation(parentColumn = "id", entityColumn = "pokemonId")
    val statEntities: List<StatDbEntity>,
    @Relation(parentColumn = "id", entityColumn = "pokemonId")
    val typeEntities: List<TypeDbEntity>,
)
