package com.example.pokedex_2_0.network

import com.example.pokedex_2_0.data.models.request.Pokemon
import com.example.pokedex_2_0.data.models.request.PokemonRequest
import com.example.pokedex_2_0.util.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//https://pokeapi.co/api/v2/pokemon?limit=10&offset=0
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PokeService {

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
object PokeApi {
    val retrofitService : PokeService by lazy {
        retrofit.create(PokeService::class.java)
    }
}
