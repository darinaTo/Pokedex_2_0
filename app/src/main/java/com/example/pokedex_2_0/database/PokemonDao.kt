package com.example.pokedex_2_0.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("select image from pokemon")
    fun getPokemonImg() : Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemonEntity: PokemonEntity)
}



