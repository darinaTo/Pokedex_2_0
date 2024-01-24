package com.example.pokedex_2_0.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val image : String,
    val name : String
)
