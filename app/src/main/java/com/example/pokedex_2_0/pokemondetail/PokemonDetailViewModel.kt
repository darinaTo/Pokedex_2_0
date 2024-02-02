package com.example.pokedex_2_0.pokemondetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_2_0.repository.PokemonRepository
import com.example.pokedex_2_0.util.Resource
import com.example.pokedex_2_0.util.UiStateDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(UiStateDetail())
    val uiState: StateFlow<UiStateDetail> = _uiState.asStateFlow()

    private val pokemonFlow = pokemonRepository.pokemonInfo.onEach { pokemons ->
        Log.d("mytag", " foreach: ${pokemons.name}")
        _uiState.update { it.copy(pokemonInfo = Resource.Success(data = pokemons)) }
    }

    init {
        val pokemonName =  savedStateHandle.get<String>("pokemonName")
        Log.d("mytag", "init ${pokemonName!!}")
        getPokemonInfo(pokemonName)
        pokemonFlow.launchIn(viewModelScope)
    }

    private fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            pokemonRepository.getPokemonInfo(pokemonName)
        }
    }
}
