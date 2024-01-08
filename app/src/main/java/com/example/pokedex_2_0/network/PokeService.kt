package com.example.pokedex_2_0.network

import com.example.pokedex_2_0.data.models.request.Pokemon
import com.example.pokedex_2_0.data.models.request.PokemonRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://pokeapi.co/api/v2/pokemon?limit=10&offset=0


interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : PokemonRequest

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon

}

