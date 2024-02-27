package com.example.pokedex_2_0.util

import kotlin.math.round


    fun extractPokemonNumber(link: String): Int {
        val regex = Regex("/pokemon/(\\d+)/")
        val matchResult = regex.find(link)
        return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: -1
    }

     fun getPokemonImageUrl(number: Int): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png "
    }

    fun covertValue(value : Int) : Float =  round(value * 100f) / 1000f
