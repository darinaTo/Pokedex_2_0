package com.example.pokedex_2_0.data.models.request.pokemondetail

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)