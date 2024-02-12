package com.example.pokedex_2_0.util

import com.example.pokedex_2_0.data.models.PokemonUiEntity

data class UiStateList(
    val pokemon: List<PokemonUiEntity> = emptyList(),
    val status: Status = Status.LOADING,
)
