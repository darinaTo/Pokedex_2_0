package com.example.pokedex_2_0.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonEntity::class],
    version = 1, exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
