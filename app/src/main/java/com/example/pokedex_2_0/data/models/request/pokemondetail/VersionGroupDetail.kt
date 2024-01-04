package com.example.pokedex_2_0.data.models.request.pokemondetail

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: MoveLearnMethod,
    val version_group: VersionGroup
)