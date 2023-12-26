package com.example.pokedex_2_0.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_2_0.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _status = MutableLiveData<PokemonApiStatus>()
    val status: LiveData<PokemonApiStatus> = _status

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList : LiveData<List<Pokemon>> = _pokemonList

    /* fun calcColor(drawable: Drawable, onFinish: (Color) -> Unit) {
      val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

         Palette.from(bitmap).generate{palette ->
             palette?.dominantSwatch?.rgb?.let {color ->
                 onFinish(Color(color))
             }
         }
     }*/

    private fun getPokemon() {
        viewModelScope.launch {
            _status.value = PokemonApiStatus.LOADING
        }
        try {
          _status.value = PokemonApiStatus.DONE
/*
            _pokemonList.value = PokeApi.retrofitService.getPokemonList(0, 10)
*/
        } catch (e : Exception) {
          _status.value = PokemonApiStatus.ERROR
        }
    }
}