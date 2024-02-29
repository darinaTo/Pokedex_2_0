package com.example.pokedex_2_0.data.impl

import com.example.pokedex_2_0.data.service.localServices.PokemonDao
import com.example.pokedex_2_0.data.service.networkSerivices.PokeApi
import com.example.pokedex_2_0.data.service.networkSerivices.mapStatToDatabaseModel
import com.example.pokedex_2_0.data.service.networkSerivices.mapToDatabaseModel
import com.example.pokedex_2_0.data.service.networkSerivices.mapToUiEntity
import com.example.pokedex_2_0.data.service.networkSerivices.mapTypeToDatabaseModel
import com.example.pokedex_2_0.domain.entities.PokemonUiEntity
import com.example.pokedex_2_0.domain.entities.PokemonUiInfoEntity
import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail.PokemonInfoApiResponse
import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonList.PokemonApiResponse
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val dao: PokemonDao,
    private val api: PokeApi
) {
    private val _errorFlow = MutableSharedFlow<Error>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val errorFlow = _errorFlow.asSharedFlow()


    suspend fun getPokemonInfoByName(name: String): Flow<PokemonUiInfoEntity> {
        return withContext(Dispatchers.IO) {
            dao.getPokemonInfo(name).also { flow ->
                if (flow.firstOrNull() == null) {
                    getPokemonInfo(name)
                }
            }.filterNotNull().map { it.mapToUiEntity() }
        }
    }
suspend fun getPokemonListByOffset(offset: Int) :  Flow<List<PokemonUiEntity>> {
   return  withContext(Dispatchers.IO) {
         dao.getListPokemon().also { flow ->
             if (flow.firstOrNull()?.isEmpty() == true) {
                 getPokemonList(offset)
             }
         }.filterNotNull().map { it.mapToUiEntity() }
     }
}
    suspend fun getPokemonList(offset: Int) {
            runCatching {
                api.getPokemonList(offset)
            }.onSuccess { pokemonData ->
                savePokemonList(pokemonData)
            }.onFailure { exception ->
               _errorFlow.tryEmit(Error(exception))
            }
    }

    //create separate interface for repository todo you may do it later on the final stage
    private suspend fun getPokemonInfo(name: String) {
            runCatching {
                api.getPokemonInfo(name)
            }.onSuccess { pokemonData ->
                savePokemonInfo(pokemonData)
            }.onFailure { exception ->
                _errorFlow.tryEmit(Error(exception))
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
