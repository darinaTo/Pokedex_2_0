package com.example.pokedex_2_0.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.pokedex_2_0.data.models.Pokemon

@Dao
interface PokemonDao {
    @Query("select * from pokemon")
    fun getAllPokemon() : LiveData<List<Pokemon>>

  /*  @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pokemonItem: PokedexItem)*/
}



