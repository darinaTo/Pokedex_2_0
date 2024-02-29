package com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokedex_2_0.data.constants.Constants.POKEMON_INFO_TABLE

@Entity(tableName = POKEMON_INFO_TABLE)
data class PokemonInfoDbEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "image") val image : String
)
