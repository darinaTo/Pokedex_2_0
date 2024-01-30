package com.example.pokedex_2_0.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex_2_0.database.entities.PokemonDBEntity
import com.example.pokedex_2_0.database.entities.PokemonInfoDBEntity
import com.example.pokedex_2_0.database.entities.PokemonInfoFullInfo
import com.example.pokedex_2_0.database.entities.StatEntityDB
import com.example.pokedex_2_0.database.entities.TypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("select * from pokemon")
    fun getListPokemon(): Flow<List<PokemonDBEntity>>

    @Query("select * from pokemon_info")
    fun getPokemonInfo(): Flow<PokemonInfoFullInfo>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokemonDBEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfoList: PokemonInfoDBEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(typesList: List<TypeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(statsList: List<StatEntityDB>)
}
