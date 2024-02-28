package com.example.pokedex_2_0.utils

import androidx.compose.ui.graphics.Color
import com.example.pokedex_2_0.domain.entities.PokemonUiInfoEntity

data class UiStateDetail(
    val pokemonInfo: PokemonUiInfoEntity = PokemonUiInfoEntity(),
    val status: Status = Status.LOADING,
    val pokemonImg: String = "",
    val dominantColor: Color = Color.White,
    val isLoading : Boolean = true,
    val errorMessage : String = ""

)

