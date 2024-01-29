package com.example.pokedex_2_0.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.pokedex_2_0.data.models.PokemonUiInfoEntity
import com.example.pokedex_2_0.repository.PokemonRepository
import com.example.pokedex_2_0.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository: PokemonRepository) :
    ViewModel() {

//    suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonInfoApiResponse> {
//        return repository.getPokemonInfoFromApi(pokemonName)
//    }

    //TODO: Please add uiState as in PokemonViewModel example and observe it on View side.
    // You may use collectAsStateWithLifecycle() as you use it already in PokemonListScreen
    // DO NOT FORGET add .launchIn(viewModelScope) for every flow which would be used

    suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonUiInfoEntity> {
        repository.getPokemonInfo(pokemonName)
        //TODO: STUB !!! Please rewrite to uiState observing using onEach{}
        // Due to .first() using there will be displayed only first pokemon from db
        val res = repository.pokemonInfoFlow.first()
        return Resource.Success(data = res)
    }
}
