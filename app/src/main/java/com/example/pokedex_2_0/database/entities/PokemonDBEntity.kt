package com.example.pokedex_2_0.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedex_2_0.util.Constants.POKEMON_TABLE

@Entity(tableName = POKEMON_TABLE)
data class PokemonDBEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "name") val name: String,
)
