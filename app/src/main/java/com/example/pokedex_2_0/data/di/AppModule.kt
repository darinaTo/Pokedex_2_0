package com.example.pokedex_2_0.data.di

import android.content.Context
import androidx.room.Room
import com.example.pokedex_2_0.database.PokemonDao
import com.example.pokedex_2_0.database.PokemonDatabase
import com.example.pokedex_2_0.network.PokeApi
import com.example.pokedex_2_0.repository.PokemonRepository
import com.example.pokedex_2_0.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providePokemonRepository(dao: PokemonDao, api: PokeApi) = PokemonRepository(dao, api)

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Singleton
    @Provides
    fun providesDao(appDatabase: PokemonDatabase): PokemonDao {
      return appDatabase.pokemonDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): PokemonDatabase {
        return Room.databaseBuilder(
            appContext,
            PokemonDatabase::class.java,
            "pokemon_database"
        ).build()
    }
}


