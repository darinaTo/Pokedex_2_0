package com.example.pokedex_2_0.di

import com.example.pokedex_2_0.data.service.localServices.PokemonDao
import com.example.pokedex_2_0.data.service.networkSerivices.PokeApi
import com.example.pokedex_2_0.data.impl.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun providePokemonRepository(dao: PokemonDao, api: PokeApi): PokemonRepository =
        PokemonRepository(dao, api)
}
