package com.example.pokedex_2_0.data.service.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.PokemonInfoDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.PokemonInfoFullInfo
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.StatDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.TypeDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonList.PokemonDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("select * from pokemon")
    fun getListPokemon(): Flow<List<PokemonDbEntity>>

    @Transaction
    @Query("select * from pokemon_info where name =:name")
    fun getPokemonInfo(name : String): Flow<PokemonInfoFullInfo?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonList: List<PokemonDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfoList: PokemonInfoDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(typesList: List<TypeDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(statsList: List<StatDbEntity>)
}
