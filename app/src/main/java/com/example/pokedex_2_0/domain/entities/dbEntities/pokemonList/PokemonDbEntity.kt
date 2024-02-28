package com.example.pokedex_2_0.domain.entities.dbEntities.pokemonList

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedex_2_0.data.constants.Constants.POKEMON_TABLE

@Entity(tableName = POKEMON_TABLE)
data class PokemonDbEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "name") val name: String,
)
