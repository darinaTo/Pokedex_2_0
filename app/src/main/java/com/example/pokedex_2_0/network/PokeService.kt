package com.example.pokedex_2_0.network

import com.example.pokedex_2_0.data.models.request.PokemonApiResponse
import com.example.pokedex_2_0.data.models.request.pokemondetail.PokemonInfoApiResponse
import com.example.pokedex_2_0.util.Constants.LIMIT
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = LIMIT
    ) : PokemonApiResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): PokemonInfoApiResponse

}

