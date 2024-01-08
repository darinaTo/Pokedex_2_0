package com.example.pokedex_2_0.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.pokedex_2_0.data.models.request.Pokemon
import com.example.pokedex_2_0.repository.PokemonRepository
import com.example.pokedex_2_0.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val repository : PokemonRepository)  : ViewModel() {
suspend fun getPokemonInfo(pokemonName : String) : Resource<Pokemon> {
return repository.getPokemonInfo(pokemonName)
}
}