package com.example.pokedex_2_0.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_state")
data class PokemonStat(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val baseStat: Int
)
