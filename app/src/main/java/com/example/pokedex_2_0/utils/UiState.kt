package com.example.pokedex_2_0.utils

import androidx.compose.ui.graphics.Color
import com.example.pokedex_2_0.domain.entities.PokemonUiEntity

data class UiState(
    val pokemon: List<PokemonUiEntity> = emptyList(),
    val status: Status = Status.LOADING,
    val defaultColor: Color = Color.White,
    val isLoading: Boolean = true,
    val errorMessage: String = "",
)
