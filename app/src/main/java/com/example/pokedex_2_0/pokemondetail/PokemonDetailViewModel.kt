package com.example.pokedex_2_0.pokemondetail

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


    private val pokemonName = requireNotNull(savedStateHandle.get<String>("pokemonName"))
    private val pokemonFlow = pokemonRepository.getPokemonInfoByName(pokemonName).onEach { pokemons ->
        _uiState.update { it.copy(pokemonInfo = Resource.Success(data = pokemons)) }
    }
    init {
            getPokemonInfo(pokemonName)
            pokemonFlow.launchIn(viewModelScope)
    }

    private fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            pokemonRepository.getPokemonInfo(pokemonName)
        }
    }
}
