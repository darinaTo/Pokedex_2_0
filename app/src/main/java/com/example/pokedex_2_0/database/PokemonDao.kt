package com.example.pokedex_2_0.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex_2_0.database.entities.PokemonEntity
import com.example.pokedex_2_0.database.entities.PokemonInfoDBEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("select * from pokemon")
     fun getListPokemon() : Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(pokemonList: List<PokemonEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(pokemonInfo: PokemonInfoDBEntity)


}



