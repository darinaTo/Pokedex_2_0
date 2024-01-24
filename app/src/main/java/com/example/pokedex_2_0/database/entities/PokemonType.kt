package com.example.pokedex_2_0.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_type")
data class PokemonType (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val pokemonTypeId : Int,
    val name : String
)
