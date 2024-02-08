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
    fun getPokemonInfoByName(name: String): Flow<PokemonUiInfoEntity> {
        return dao.getPokemonInfo(name).map { it.mapToUiEntity() }
    }

    suspend fun getPokemonList(offset: Int) {
        withContext(Dispatchers.IO) {
            runCatching {
                //TODO check if pokemon exist in db
                api.getPokemonList(offset)
            }.onSuccess {pokemonData->
                savePokemonList(pokemonData)
            }.onFailure { exception ->
                throw RuntimeException("Can't get pokemon list, api status: ${exception.message}")
            }
        }
    }

    //create separate interface for repository todo you may do it later on the final stage
    suspend fun getPokemonInfo(name: String) {
        withContext(Dispatchers.IO) {
            runCatching {
                api.getPokemonInfo(name)
            }.onSuccess {pokemonData ->
                savePokemonInfo(pokemonData)
            }.onFailure {exception ->
                throw RuntimeException("Can't get the pokemon info, api status: ${exception.message}")
            }
        }
    }

    private suspend fun savePokemonList(pokemonList: PokemonApiResponse) {
        val databaseEntities = pokemonList.mapToDatabaseModel()
        dao.insertAll(databaseEntities)
    }

    private suspend fun savePokemonInfo(pokemonInfoApiResponse: PokemonInfoApiResponse) {
        val pokemonEntity = pokemonInfoApiResponse.mapToDatabaseModel()
        val pokemonId = pokemonEntity.id

        val typeEntities = pokemonInfoApiResponse.types.mapTypeToDatabaseModel(pokemonId)
        val statEntities = pokemonInfoApiResponse.stats.mapStatToDatabaseModel(pokemonId)
            dao.insertPokemonInfo(pokemonEntity)
            dao.insertTypes(typeEntities)
            dao.insertStats(statEntities)
    }
}
