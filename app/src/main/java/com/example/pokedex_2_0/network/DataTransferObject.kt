package com.example.pokedex_2_0.network

import com.example.pokedex_2_0.data.models.PokemonEntry
import com.example.pokedex_2_0.database.entities.PokemonEntity



fun List<PokemonEntry>.asModelToDatabase(): List<PokemonEntity> {
    return map {
        PokemonEntity(
            id = it.number,
            image = it.imageUrl,
            name = it.pokemonName
        )
    }
}

fun List<PokemonEntity>.asDatabaseToModel() : List<PokemonEntry> {
    return map {
        PokemonEntry(
            pokemonName = it.name,
            imageUrl = it.image,
            number = it.id
        )
    }
}


fun PokemonEntry.asOneModelToDatabase(): PokemonEntity {
   return PokemonEntity(
       id = this.number,
       image = this.imageUrl,
       name =  this.pokemonName
   )
}