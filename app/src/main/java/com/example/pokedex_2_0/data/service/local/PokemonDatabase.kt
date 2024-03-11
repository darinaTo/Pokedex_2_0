package com.example.pokedex_2_0.data.service.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.PokemonInfoDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.StatDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.TypeDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonList.PokemonDbEntity

@Database(
    entities = [PokemonDbEntity::class, PokemonInfoDbEntity::class,
        StatDbEntity::class, TypeDbEntity::class],
    version = 1, exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
