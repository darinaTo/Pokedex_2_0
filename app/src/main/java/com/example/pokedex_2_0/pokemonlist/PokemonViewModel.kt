package com.example.pokedex_2_0.pokemonlist

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex_2_0.data.models.PokemonEntry
import com.example.pokedex_2_0.repository.PokemonRepository
import com.example.pokedex_2_0.util.Constants.PAGE_SIZE
import com.example.pokedex_2_0.util.PokemonApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

    private val _status = MutableLiveData<PokemonApiStatus>()
    private var currentPage = 0

    val status: LiveData<PokemonApiStatus> = _status

    private val _pokemonList = MutableStateFlow<List<PokemonEntry>>(emptyList())
    val pokemonList: StateFlow<List<PokemonEntry>> = _pokemonList.asStateFlow()


    init {
        savePokemon()
        getPokemon()
    }

    fun calcColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap!!).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { color ->
                onFinish(Color(color))
            }
        }
    }

    private fun savePokemon() = viewModelScope.launch {
        try {
            pokemonRepository.savePokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)
        } catch (e: IOException) {
            throw RuntimeException("Can't save pokemon to data base $e")
        }
    }

    fun getPokemon() {
        viewModelScope.launch {
            _status.value = PokemonApiStatus.LOADING
            try {
                val pokemonEntry = pokemonRepository.pokemonList.value!!
                currentPage++
                _pokemonList.value = _pokemonList.value.plus(pokemonEntry)
                _status.value = PokemonApiStatus.DONE


            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
                throw RuntimeException("Can't get pokemon from data base, $e")
            }
        }
    }


}