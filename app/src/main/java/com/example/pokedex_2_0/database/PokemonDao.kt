package com.example.pokedex_2_0.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex_2_0.data.models.Pokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("select * from pokemon")
    fun getAllPokemon() : Flow<List<Pokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemon: Pokemon)
}



