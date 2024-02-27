package com.example.pokedex_2_0.util

import androidx.compose.ui.graphics.Color
import com.example.pokedex_2_0.data.models.PokemonUiInfoEntity

data class UiStateDetail(
    val pokemonInfo: PokemonUiInfoEntity = PokemonUiInfoEntity(),
    val status: Status = Status.LOADING,
    val pokemonImg: String = "",
    val dominantColor: Color = Color.White
){

    val isLoading
        get() = status == Status.LOADING
}
