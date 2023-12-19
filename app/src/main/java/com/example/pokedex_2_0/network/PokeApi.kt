package com.example.pokedex_2_0.network

import com.example.pokedex_2_0.data.PokemonList
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL ="https://pokeapi.co/api/v2"
interface PokeApi {

    @GET("/pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit : Int,
        @Query("offset") offset: Int
    ) : PokemonList
}