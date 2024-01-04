package com.example.pokedex_2_0.pokemondetail

import androidx.lifecycle.ViewModel
import com.example.pokedex_2_0.data.models.request.Pokemon
import com.example.pokedex_2_0.network.PokeApi
import com.example.pokedex_2_0.repository.PokemonRepository
import com.example.pokedex_2_0.util.Resource

class PokemonDetailViewModel : ViewModel() {
    private val repository = PokemonRepository(PokeApi)
suspend fun getPokemonInfo(pokemonName : String) : Resource<Pokemon> {
return repository.getPokemonInfo(pokemonName)
}
}