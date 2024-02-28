package com.example.pokedex_2_0.data.service.localServices

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokedex_2_0.data.service.localServices.PokemonDao
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonList.PokemonDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.PokemonInfoDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.StatDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.TypeDbEntity

@Database(
    entities = [PokemonDbEntity::class, PokemonInfoDbEntity::class,
        StatDbEntity::class, TypeDbEntity::class],
    version = 1, exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
