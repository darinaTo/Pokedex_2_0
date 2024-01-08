package com.example.pokedex_2_0.repository

import com.example.pokedex_2_0.data.models.request.Pokemon
import com.example.pokedex_2_0.data.models.request.PokemonRequest
import com.example.pokedex_2_0.network.PokeApi
import com.example.pokedex_2_0.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor (/*private val dao: PokemonDao,*/
                                                private val api : PokeApi) {

    suspend fun getPokemonList(limit : Int, offset:Int) : Resource<PokemonRequest> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e : Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(name: String) : Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(name)
        } catch (e : Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

   /* suspend fun savePokemonList(limit : Int, offset:Int)  {
        withContext(Dispatchers.IO) {
            val pokemonList = getPokemonList(limit, offset)
            dao.insertAll()
        }
    }*/
}
