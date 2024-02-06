package com.example.pokedex_2_0.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.pokedex_2_0.util.Constants.POKEMON_STAT_TABLE

@Entity(tableName = POKEMON_STAT_TABLE ,
    foreignKeys = [ForeignKey(
        entity = PokemonInfoDBEntity::class,
        parentColumns = ["id"],
        childColumns = ["pokemonId"],
        onDelete = ForeignKey.CASCADE
    )])
data class StatEntityDB (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int = 0,
    @ColumnInfo(name = "base_stat") val baseStat : Int,
    @ColumnInfo(name = "name") val name : String,
    val pokemonId : Int
)