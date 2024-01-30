package com.example.pokedex_2_0.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokedex_2_0.data.models.PokemonUiEntity

data class UiState(
    val pokemons: List<PokemonUiEntity> = emptyList(),
    val status: LiveData<PokemonApiStatus> = MutableLiveData<PokemonApiStatus>(),
)
