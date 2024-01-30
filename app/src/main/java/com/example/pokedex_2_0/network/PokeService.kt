package com.example.pokedex_2_0.network

import com.example.pokedex_2_0.data.models.request.PokemonApiResponse
import com.example.pokedex_2_0.data.models.request.pokemondetail.PokemonInfoApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://pokeapi.co/api/v2/pokemon?limit=10&offset=0

// TODO: it is better to rename file to PokeApi to avoid misleading. You may use rc -> refactoring -> rename in files tree
interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ) : PokemonApiResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): PokemonInfoApiResponse

}

