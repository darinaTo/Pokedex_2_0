package com.example.pokedex_2_0.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_2_0.data.constants.Constants.ERROR_MESSAGE
import com.example.pokedex_2_0.data.constants.Constants.OFFSET
import com.example.pokedex_2_0.data.impl.PokemonRepository
import com.example.pokedex_2_0.utils.Status
import com.example.pokedex_2_0.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    private var currentPage = 0

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val pokemonFlow = pokemonRepository.pokemonList.onEach { pokemon ->
        _uiState.update { it.copy(pokemon = pokemon, status = Status.DONE) }
    }

    private val errorFlow = pokemonRepository.errorFlow.onEach {
        _uiState.update { it.copy(errorMessage = ERROR_MESSAGE) }
    }


    init {
        viewModelScope.launch {
            pokemonRepository.getPokemonListByOffset(currentPage)
            pokemonFlow.launchIn(viewModelScope)
            errorFlow.launchIn(viewModelScope)
        }
    }

    fun getPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            currentPage += OFFSET
            pokemonRepository.getPokemonListByOffset(currentPage)
        }
    }
}
