package com.example.pokedex_2_0.network

import com.example.pokedex_2_0.data.models.PokemonUiEntity
import com.example.pokedex_2_0.data.models.PokemonUiInfoEntity
import com.example.pokedex_2_0.data.models.request.PokemonApiEntity
import com.example.pokedex_2_0.data.models.request.pokemondetail.PokemonInfoApiResponse
import com.example.pokedex_2_0.data.models.request.pokemondetail.Stat
import com.example.pokedex_2_0.data.models.request.pokemondetail.StatX
import com.example.pokedex_2_0.data.models.request.pokemondetail.Type
import com.example.pokedex_2_0.data.models.request.pokemondetail.TypeX
import com.example.pokedex_2_0.database.entities.PokemonDBEntity
import com.example.pokedex_2_0.database.entities.PokemonInfoDBEntity
import com.example.pokedex_2_0.database.entities.PokemonInfoFullInfo

fun List<PokemonApiEntity>.mapToDatabaseModel(): List<PokemonDBEntity> {
    return this.map { pokemon ->
        val number = extractPokemonNumber(pokemon.url)
        val url = getPokemonImageUrl(number)
        PokemonDBEntity(
            id = number,
            image = url,
            name = pokemon.name
        )
    }
}

fun List<PokemonDBEntity>.mapToUiEntity(): List<PokemonUiEntity> {
    return map {
        PokemonUiEntity(
            pokemonName = it.name,
            imageUrl = it.image,
            number = it.id
        )
    }
}

fun PokemonInfoApiResponse.mapToDatabaseModel(): PokemonInfoDBEntity {
    return PokemonInfoDBEntity(
        id = this.id,
        name = this.name,
        weight = this.weight,
        height = this.height
    )
}

fun PokemonInfoFullInfo.mapToUiEntity(): PokemonUiInfoEntity {
    return  PokemonUiInfoEntity(
        id = this.pokemonEntity.id,
            name = this.pokemonEntity.name,
            height = this.pokemonEntity.height,
            weight = this.pokemonEntity.weight,
            stats = this.statEntities.map {
                Stat(
                    base_stat = it.baseStat,
                    statInfo = StatX(it.name)
                )
            },
            types = this.typeEntities.map {
                Type(
                    type = TypeX(it.name)
                )
            }
        )

}


// TODO: Mapping to PokemonInfoApiResponse is TEMPORARY SOLUTION replace to UI entity when it would be ready
/*
fun PokemonInfoFullInfo.mapToUiEntity(): PokemonInfoApiResponse {
    return PokemonInfoApiResponse(
        height = this.pokemonEntity.height,
        id = this.pokemonEntity.id,
        name = this.pokemonEntity.name,
        stats = this.statEntities.map {
            Stat(
                it.baseStat,
                StatX(it.name)
            )
        }, // TODO: check it this could be simplified
        types = this.typeEntities.map { Type(TypeX(it.name)) },// TODO: check it this could be simplified
        weight = this.pokemonEntity.weight,
    )
}
*/

private fun extractPokemonNumber(link: String): Int {
    val regex = Regex("/pokemon/(\\d+)/")
    val matchResult = regex.find(link)
    return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: -1 // TODO: check -1 case
}

private fun getPokemonImageUrl(number: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png "
}