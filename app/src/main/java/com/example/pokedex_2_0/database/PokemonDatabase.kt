package com.example.pokedex_2_0.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedex_2_0.database.entities.PokemonEntity
import com.example.pokedex_2_0.database.entities.PokemonInfo
import com.example.pokedex_2_0.database.entities.PokemonInfoStat
import com.example.pokedex_2_0.database.entities.PokemonStat
import com.example.pokedex_2_0.database.entities.PokemonType

@Database(
    entities = [PokemonEntity::class, PokemonInfo::class, PokemonInfoStat::class,
               PokemonType::class, PokemonStat::class],
    version = 2, exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
