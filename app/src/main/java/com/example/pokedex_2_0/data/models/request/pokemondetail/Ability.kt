package com.example.pokedex_2_0.data.models.request.pokemondetail

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)