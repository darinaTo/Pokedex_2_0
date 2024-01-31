package com.example.pokedex_2_0.util

import com.example.pokedex_2_0.data.models.PokemonUiEntity

data class UiState(
    val pokemons: List<PokemonUiEntity> = emptyList(),
    val status: PokemonApiStatus = PokemonApiStatus.LOADING,
)
