package com.example.pokedex_2_0.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_info")
//TODO: create const for table name and add column_info
//Todo: можна перевести телефон в режим розробника
data class PokemonInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val typeId : Int,
    val name: String,
    val weight: Int,
    val height: Int,
)
