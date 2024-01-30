package com.example.pokedex_2_0.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex_2_0.data.models.PokemonUiEntity
import com.example.pokedex_2_0.repository.PokemonRepository
import com.example.pokedex_2_0.util.Constants.PAGE_SIZE
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

    //TODO: it is better for simplicity to use one type of data container LiveData of Flow
    // and these fields also could be part of uiState
    private val _status = MutableLiveData<PokemonApiStatus>()
    private var currentPage = 0

    val status: LiveData<PokemonApiStatus> = _status

    //TODO: Pokemon list should be flow and update List<PokemonEntry> as part of uiState
    // please see example below
    private val _pokemonList = MutableStateFlow<List<PokemonUiEntity>>(emptyList())
    val pokemonList: StateFlow<List<PokemonUiEntity>> = _pokemonList.asStateFlow()


    // TODO: example
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<List<PokemonUiEntity>> = _pokemonList.asStateFlow() //todo collect this state in view

    private val pokemonFlow = pokemonRepository.pokemonList.onEach { pokemons ->
        _uiState.update { it.copy(pokemons = pokemons) }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: PAGE_SIZE could be as default parameter
            pokemonRepository.savePokemonList(PAGE_SIZE, currentPage * PAGE_SIZE) // TODO: better to rename to getPokemonList()
            refreshPokemonList() // TODO: this line is redundant in case of using pokemonFlow
            pokemonFlow.launchIn(viewModelScope) // todo required for correct work
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
//            _status.value = PokemonApiStatus.LOADING
            _status.postValue(PokemonApiStatus.LOADING)

            try {
                pokemonRepository.savePokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)
                currentPage++
                refreshPokemonList()
                _status.value = PokemonApiStatus.DONE


            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
                throw RuntimeException("Can't get pokemon from data base, $e")
            }
        }
    }

    // TODO: in case of using flows this method would be redundant
    private suspend fun refreshPokemonList() {
        //TODO:onEach in Flow
            pokemonRepository.pokemonList.collect {

                Log.i("mytag", "refreshPokemonList VM: ${it.map { it.pokemonName }}")
                _pokemonList.value = it
            }
    }
}

// TODO: add rest required fields and move to separate file
