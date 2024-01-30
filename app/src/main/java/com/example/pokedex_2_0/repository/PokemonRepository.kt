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

    //TODO: It is better to rename as main purpose is getting data
    suspend fun savePokemonList(limit: Int, offset: Int) {
        val pokemonFromApi = getPokemonList(limit, offset)
        val pokemonData = pokemonFromApi.data
        if (pokemonFromApi is Resource.Success && pokemonData != null) {
            val databaseEntities = pokemonData.pokemonApiEntities.mapToDatabaseModel()
            dao.insertAll(databaseEntities)
        } else {
            // TODO: handle Error or null case. f.e. return data from db
        }
    }

    //create separate interface for repository todo you may do it later on the final stage
    suspend fun getPokemonInfo(name: String) {
        withContext(Dispatchers.IO) {
            // TODO: Later this part may be optimized
            val pokemonInfo = getPokemonInfoFromApi(name)
            val pokemonData = pokemonInfo.data
            if (pokemonInfo is Resource.Success && pokemonData != null) {
                savePokemonInfo(pokemonData)
            } else {
                // TODO: Handle error
            }
        }
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
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

    //changed Resource to Result from kt todo this is not obligatory some sort of box solution
    private suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonApiResponse> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.")
        }
        return Resource.Success(response)
    }

}
