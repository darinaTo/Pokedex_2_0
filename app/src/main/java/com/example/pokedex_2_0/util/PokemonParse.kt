package com.example.pokedex_2_0.util

import androidx.compose.ui.graphics.Color
import com.example.pokedex_2_0.data.models.request.pokemondetail.Stat
import com.example.pokedex_2_0.data.models.request.pokemondetail.Type
import com.example.pokedex_2_0.ui.theme.AtkColor
import com.example.pokedex_2_0.ui.theme.DefColor
import com.example.pokedex_2_0.ui.theme.HPColor
import com.example.pokedex_2_0.ui.theme.SpAtkColor
import com.example.pokedex_2_0.ui.theme.SpDefColor
import com.example.pokedex_2_0.ui.theme.SpdColor
import com.example.pokedex_2_0.ui.theme.TypeBug
import com.example.pokedex_2_0.ui.theme.TypeDark
import com.example.pokedex_2_0.ui.theme.TypeDragon
import com.example.pokedex_2_0.ui.theme.TypeElectric
import com.example.pokedex_2_0.ui.theme.TypeFairy
import com.example.pokedex_2_0.ui.theme.TypeFighting
import com.example.pokedex_2_0.ui.theme.TypeFire
import com.example.pokedex_2_0.ui.theme.TypeFlying
import com.example.pokedex_2_0.ui.theme.TypeGhost
import com.example.pokedex_2_0.ui.theme.TypeGrass
import com.example.pokedex_2_0.ui.theme.TypeGround
import com.example.pokedex_2_0.ui.theme.TypeIce
import com.example.pokedex_2_0.ui.theme.TypeNormal
import com.example.pokedex_2_0.ui.theme.TypePoison
import com.example.pokedex_2_0.ui.theme.TypePsychic
import com.example.pokedex_2_0.ui.theme.TypeRock
import com.example.pokedex_2_0.ui.theme.TypeSteel
import com.example.pokedex_2_0.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
    return when (type.type.name.toLowerCase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when (stat.stat.name.toLowerCase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when (stat.stat.name.toLowerCase()) {
        "hp" -> "HP"
        "attack" -> "ATK"
        "defense" -> "DEF"
        "speed" -> "SPD"
        else -> ""
    }
}

