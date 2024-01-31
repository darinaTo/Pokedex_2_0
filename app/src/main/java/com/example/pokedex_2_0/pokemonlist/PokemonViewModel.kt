package com.example.pokedex_2_0.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex_2_0.repository.PokemonRepository
import com.example.pokedex_2_0.util.Constants.OFFSET
import com.example.pokedex_2_0.util.PokemonApiStatus
import com.example.pokedex_2_0.util.UiState
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

    private val pokemonFlow = pokemonRepository.pokemonList.onEach { pokemons ->
        _uiState.update { it.copy(pokemons = pokemons) }
    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            pokemonRepository.getPokemonList(currentPage)
            pokemonFlow.launchIn(viewModelScope)
        }
    }

    fun calcColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap!!).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { color ->
                onFinish(Color(color))
            }
        }
    }

    fun getPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("mytag", "getPokemon: VM ${Thread.currentThread().name}")
            _uiState.update { it.copy(status = PokemonApiStatus.LOADING) }

            try {
                currentPage += OFFSET
                pokemonRepository.getPokemonList(currentPage)
                _uiState.update { it.copy(status = PokemonApiStatus.DONE) }
            } catch (e: Exception) {
                _uiState.update { it.copy(status = PokemonApiStatus.ERROR) }
                throw RuntimeException("Can't get pokemon from data base, $e")
            }
        }
    }
}
