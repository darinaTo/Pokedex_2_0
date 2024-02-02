package com.example.pokedex_2_0.util

import com.example.pokedex_2_0.data.models.PokemonUiInfoEntity

data class UiStateDetail(
    val pokemonInfo : Resource<PokemonUiInfoEntity> = Resource.Loading(),
    val status: PokemonApiStatus = PokemonApiStatus.LOADING,
    )
