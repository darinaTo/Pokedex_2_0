package com.example.pokedex_2_0.ui.viewdodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_2_0.data.impl.PokemonRepository
import com.example.pokedex_2_0.utils.Status
import com.example.pokedex_2_0.utils.UiStateDetail
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
    private val pokemonName: String
    private val pokemonImg: String
    private val dominantColor: Int

    init {
        pokemonName = requireNotNull(savedStateHandle.get<String>("pokemonName"))
        pokemonImg = requireNotNull(savedStateHandle.get<String>("pokemonImg"))
        dominantColor = requireNotNull(savedStateHandle.get<Int>("pokemonColor"))
        _uiState.update { it.copy(pokemonImg = pokemonImg, dominantColor = Color(dominantColor)) }
        viewModelScope.launch {
            getPokemonInfo(pokemonName)
        }
    }

    // TODO: parameter could be removed as pokemonName is available in method's scope
    private suspend fun getPokemonInfo(pokemonName: String) {
        pokemonRepository.getPokemonInfoByName(pokemonName).onEach { pokemon ->
                _uiState.update { it.copy(pokemonInfo = pokemon, status = Status.DONE, isLoading = false) }
        }.launchIn(viewModelScope)
    }
}
