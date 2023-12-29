package com.example.pokedex_2_0.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedex_2_0.data.models.Pokemon

@Database(entities = [Pokemon::class],
    version = 1)
abstract class PokemonDatabase :RoomDatabase() {
    abstract val pokemonDao : PokemonDao
}

@Volatile
private lateinit var INSTANCE : PokemonDatabase

fun getDatabase(context: Context) : PokemonDatabase {
    synchronized(PokemonDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PokemonDatabase::class.java,
                "pokemon_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}
