package com.example.pokedex_2_0.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonDBEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "name") val name: String,
)
//TODO: consider adding some entity for storing previous and next fielsd from API response for pagination
// This may be helpful for pagination handling