package com.example.pokedex_2_0.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedex_2_0.data.models.Pokemon

@Database(entities = [Pokemon::class],
    version = 1)
abstract class PokemonDatabase :RoomDatabase() {
    abstract val pokemonDao: PokemonDao

    companion object {
        @Volatile
        private var INSTANCE : PokemonDatabase? = null

    fun getDatabase(context: Context): PokemonDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PokemonDatabase::class.java,
                "pokemon_database"
            )
                .build()
            INSTANCE = instance
            // return instance
            instance
        }
    }
    }
}
