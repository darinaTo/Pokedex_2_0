package com.example.pokedex_2_0.repository

import com.example.pokedex_2_0.data.models.PokemonEntry
import com.example.pokedex_2_0.data.models.request.PokemonRequest
import com.example.pokedex_2_0.data.models.request.pokemondetail.Pokemon
import com.example.pokedex_2_0.database.PokemonDao
import com.example.pokedex_2_0.network.PokeApi
import com.example.pokedex_2_0.network.asDatabaseToModel
import com.example.pokedex_2_0.network.asModelToDatabase
import com.example.pokedex_2_0.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val dao: PokemonDao,
    private val api: PokeApi
) {
val pokemonList : Flow<List<PokemonEntry>> = dao.getListPokemon().map { it.asDatabaseToModel() }
    suspend fun getPokemonInfo(name: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(name)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    suspend fun savePokemonList(limit: Int, offset: Int)  {
        withContext(Dispatchers.IO) {
            val pokemonList = getPokemonList(limit, offset)

         val pokemonEntry =     pokemonList.data!!.results.mapIndexed { _, pokemon ->
                val number = if (pokemon.url.endsWith("/")) {
                    pokemon.url.dropLast(1).takeLastWhile { it.isDigit() }
                } else {
                    pokemon.url.takeLastWhile { it.isDigit() }
                }
                val url = getPokemonImageUrl(number.toInt())
                PokemonEntry(pokemon.name, url, number.toInt())
            }

             dao.insertAll(pokemonEntry.asModelToDatabase())
        }
    }
    private suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonRequest> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    private fun getPokemonImageUrl(number: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png "
    }
}
