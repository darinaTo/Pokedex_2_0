package com.example.pokedex_2_0.repository

import com.example.pokedex_2_0.data.models.PokemonUiEntity
import com.example.pokedex_2_0.data.models.PokemonUiInfoEntity
import com.example.pokedex_2_0.data.models.request.PokemonApiResponse
import com.example.pokedex_2_0.data.models.request.pokemondetail.PokemonInfoApiResponse
import com.example.pokedex_2_0.database.PokemonDao
import com.example.pokedex_2_0.network.PokeApi
import com.example.pokedex_2_0.network.mapStatToDatabaseModel
import com.example.pokedex_2_0.network.mapToDatabaseModel
import com.example.pokedex_2_0.network.mapToUiEntity
import com.example.pokedex_2_0.network.mapTypeToDatabaseModel
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
    val pokemonList: Flow<List<PokemonUiEntity>> = dao.getListPokemon().map { it.mapToUiEntity() }
    val pokemonInfoFlow: Flow<PokemonUiInfoEntity> = dao.getPokemonInfo().map { it.mapToUiEntity() }
    val url = "?limit=10&offset=0"

    suspend fun getPokemonList(offset: Int, limit: Int) {
        withContext(Dispatchers.IO) {
            val pokemonFromApi = getPokemonListApi(offset, limit)
            val pokemonData = pokemonFromApi.data
            if (pokemonFromApi is Resource.Success && pokemonData != null) {
              savePokemonList(pokemonData)
            } else {
                throw RuntimeException("Can't get pokemon list, api status: ${pokemonFromApi.message}")
            }
        }
    }

    //create separate interface for repository todo you may do it later on the final stage
    suspend fun getPokemonInfo(name: String) {
        withContext(Dispatchers.IO) {
            val pokemonInfo = getPokemonInfoFromApi(name)
            val pokemonData = pokemonInfo.data
            if (pokemonInfo is Resource.Success && pokemonData != null) {
                savePokemonInfo(pokemonData)
            } else {
             throw RuntimeException("Can't get the pokemon info, api status: ${pokemonInfo.message}")
            }
        }
    }

    private suspend fun savePokemonList(pokemonList : PokemonApiResponse) {
        val databaseEntities = pokemonList.mapToDatabaseModel()
        dao.insertAll(databaseEntities)
    }
    private suspend fun savePokemonInfo(pokemonInfoApiResponse: PokemonInfoApiResponse) {
        val pokemonEntity =  pokemonInfoApiResponse.mapToDatabaseModel()
        val pokemonId = pokemonEntity.id
        val typeEntities = pokemonInfoApiResponse.types.mapTypeToDatabaseModel(pokemonId)
        val statEntities = pokemonInfoApiResponse.stats.mapStatToDatabaseModel(pokemonId)
        dao.insertPokemonInfo(pokemonEntity)
        dao.insertTypes(typeEntities)
        dao.insertStats(statEntities)
    }

    private suspend fun getPokemonInfoFromApi(name: String): Resource<PokemonInfoApiResponse> {
        val response = try {
            api.getPokemonInfo(name)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred, exception: ${e.message}")
        }
        return Resource.Success(response)
    }

    //changed Resource to Result from kt todo this is not obligatory some sort of box solution
    private suspend fun getPokemonListApi(offset: Int, limit: Int): Resource<PokemonApiResponse> {
        val response = try {
            api.getPokemonList(offset, limit)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred, exception: ${e.message}")
        }
        return Resource.Success(response)
    }

}
