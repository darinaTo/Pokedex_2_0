package com.example.pokedex_2_0.repository

import com.example.pokedex_2_0.database.PokemonDatabase
import com.example.pokedex_2_0.network.PokeApi

class PokemonRepository(private val database: PokemonDatabase,
    private val api : PokeApi) {

   /* suspend fun getPokemonList(limit : Int, offset:Int) : Resource<PokemonList> {
        val response = try {
            api.retrofitService.getPokemonList(limit, offset)
        } catch (e : Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(name: String) : Resource<Pokemon> {
        val response = try {
            api.retrofitService.getPokemonInfo(name)
        } catch (e : Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }*/
}
