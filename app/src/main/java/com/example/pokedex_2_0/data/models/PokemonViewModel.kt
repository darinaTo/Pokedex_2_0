package com.example.pokedex_2_0.data.models

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex_2_0.network.PokeApi
import com.example.pokedex_2_0.util.Constants.PAGE_SIZE
import com.example.pokedex_2_0.util.PokemonApiStatus
import kotlinx.coroutines.launch
import java.util.Locale

class PokemonViewModel(/*private val repository: PokemonRepository*/) : ViewModel() {
    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus> = _status

    //private val _pokemonList = mutableStateOf<List<PokemonEntry>>(listOf())
    val pokemonList = mutableStateOf<List<PokemonEntry>>(listOf())

    private var currentPage = 0


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
                val request = PokeApi.retrofitService
                    .getPokemonList(PAGE_SIZE, currentPage * PAGE_SIZE)

                val pokemonEntry = request.results.mapIndexed { index, pokemon ->
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

                pokemonList.value += pokemonEntry
                _status.value = PokemonApiStatus.DONE

            } catch (e: Exception) {
                _status.value = PokemonApiStatus.ERROR
            }
        }
    }

}