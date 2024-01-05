package com.example.pokedex_2_0.util

import android.app.Application
import com.example.pokedex_2_0.database.PokemonDatabase

class InventoryApplication : Application() {
    val database : PokemonDatabase by lazy { PokemonDatabase.getDatabase(this) }
}