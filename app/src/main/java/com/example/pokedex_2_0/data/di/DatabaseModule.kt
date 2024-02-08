package com.example.pokedex_2_0.data.di

import android.content.Context
import androidx.room.Room
import com.example.pokedex_2_0.database.PokemonDao
import com.example.pokedex_2_0.database.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDao(appDatabase: PokemonDatabase): PokemonDao = appDatabase.pokemonDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): PokemonDatabase {
        return Room.databaseBuilder(
            appContext,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).fallbackToDestructiveMigration().build()
    }
}
