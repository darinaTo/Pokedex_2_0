package com.example.pokedex_2_0.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import kotlin.math.round


fun extractPokemonNumber(link: String): Int {
    val regex = Regex("/pokemon/(\\d+)/")
    val matchResult = regex.find(link)
    return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: -1
}

fun getPokemonImageUrl(number: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png "
}

fun covertValue(value: Int): Float = round(value * 100f) / 1000f

fun Drawable.calcColor( onFinish: (Color) -> Unit) {
    val bitmap = (this as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
    Palette.from(bitmap).generate { palette ->
        palette?.dominantSwatch?.rgb?.let { color ->
            onFinish(Color(color))
        }
    }
}