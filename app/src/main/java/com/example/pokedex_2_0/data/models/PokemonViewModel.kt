package com.example.pokedex_2_0.data.models

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex_2_0.network.PokeApi
import com.example.pokedex_2_0.repository.PokemonRepository
import com.example.pokedex_2_0.util.Constants.PAGE_SIZE
import com.example.pokedex_2_0.util.PokemonApiStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class PokemonViewModel() : ViewModel() {
    private val _status = MutableLiveData<PokemonApiStatus>()
    private var currentPage = 0

    val status: LiveData<PokemonApiStatus> = _status

    private val _pokemonList = MutableStateFlow<List<PokemonEntry>>(emptyList())
    val pokemonList: StateFlow<List<PokemonEntry>> = _pokemonList.asStateFlow()

    private val pokemonRepository = PokemonRepository(PokeApi)
    fun calcColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap!!).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { color ->
                onFinish(Color(color))
            }
        }
    }

    init {
        getPokemon()
    }

    private fun getPokemon() {
        viewModelScope.launch {
            _status.value = PokemonApiStatus.LOADING
            try {
                val request = pokemonRepository
                    .getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)

                val pokemonEntry = request.data!!.results.mapIndexed { index, pokemon ->
                    val number = if (pokemon.url.endsWith("/")) {
                        pokemon.url.dropLast(1).takeLastWhile { it.isDigit() }
                    } else {
                        pokemon.url.takeLastWhile { it.isDigit() }
                    }
                    val url =
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png "

                    PokemonEntry(pokemon.name.capitalize(Locale.ROOT), url, number.toInt())
                }
                currentPage++

                _pokemonList.value = _pokemonList.value.plus(pokemonEntry)
                _status.value = PokemonApiStatus.DONE

            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
            }
        }
    }

}