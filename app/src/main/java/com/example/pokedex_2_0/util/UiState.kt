package com.example.pokedex_2_0.util

import androidx.compose.ui.graphics.Color
import com.example.pokedex_2_0.data.models.PokemonUiEntity

data class UiState(
    val pokemon: List<PokemonUiEntity> = emptyList(),
    val status: Status = Status.LOADING,
    val defaultColor : Color = Color.White,
)
