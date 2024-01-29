package com.example.pokedex_2_0.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedex_2_0.database.entities.PokemonDBEntity
import com.example.pokedex_2_0.database.entities.PokemonInfoDBEntity
import com.example.pokedex_2_0.database.entities.StatEntity
import com.example.pokedex_2_0.database.entities.TypeEntity

@Database(
    entities = [PokemonDBEntity::class, PokemonInfoDBEntity::class,
        StatEntity::class, TypeEntity::class],
    version = 2, exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
