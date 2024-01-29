package com.example.pokedex_2_0.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedex_2_0.database.entities.PokemonEntity
import com.example.pokedex_2_0.database.entities.PokemonInfo

@Database(
    entities = [PokemonEntity::class, PokemonInfo::class],
    version = 2, exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
