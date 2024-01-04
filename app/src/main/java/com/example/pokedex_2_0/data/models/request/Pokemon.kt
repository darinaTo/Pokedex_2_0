package com.example.pokedex_2_0.data.models.request

import com.example.pokedex_2_0.data.models.request.pokemondetail.Ability
import com.example.pokedex_2_0.data.models.request.pokemondetail.Form
import com.example.pokedex_2_0.data.models.request.pokemondetail.GameIndice
import com.example.pokedex_2_0.data.models.request.pokemondetail.Move
import com.example.pokedex_2_0.data.models.request.pokemondetail.Species
import com.example.pokedex_2_0.data.models.request.pokemondetail.Sprites
import com.example.pokedex_2_0.data.models.request.pokemondetail.Stat
import com.example.pokedex_2_0.data.models.request.pokemondetail.Type

data class Pokemon(
    val abilities: List<Ability>,
    val base_experience: Int,
    val forms: List<Form>,
    val game_indices: List<GameIndice>,
    val height: Int,
    val held_items: List<Any>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val past_abilities: List<Any>,
    val past_types: List<Any>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Int
)