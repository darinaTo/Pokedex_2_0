package com.example.pokedex_2_0.network

import com.example.pokedex_2_0.data.models.PokemonUiEntity
import com.example.pokedex_2_0.data.models.PokemonUiInfoEntity
import com.example.pokedex_2_0.data.models.request.PokemonApiResponse
import com.example.pokedex_2_0.data.models.request.pokemondetail.PokemonInfoApiResponse
import com.example.pokedex_2_0.data.models.request.pokemondetail.Stat
import com.example.pokedex_2_0.data.models.request.pokemondetail.StatX
import com.example.pokedex_2_0.data.models.request.pokemondetail.Type
import com.example.pokedex_2_0.data.models.request.pokemondetail.TypeX
import com.example.pokedex_2_0.database.entities.PokemonDBEntity
import com.example.pokedex_2_0.database.entities.PokemonInfoDBEntity
import com.example.pokedex_2_0.database.entities.PokemonInfoFullInfo
import com.example.pokedex_2_0.database.entities.StatEntityDB
import com.example.pokedex_2_0.database.entities.TypeEntity

fun PokemonApiResponse.mapToDatabaseModel(): List<PokemonDBEntity> {
    return this.pokemonApiEntities.map { pokemon ->
        val number = extractPokemonNumber(pokemon.url)
        val url = getPokemonImageUrl(number)
        PokemonDBEntity(
            id = number,
            image = url,
            name = pokemon.name,
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
fun List<Type>.mapTypeToDatabaseModel(pokemonId : Int) : List<TypeEntity> {
    return map {
        TypeEntity(
            name = it.type.name,
            pokemonId = pokemonId
        )
    }
}

fun List<Stat>.mapStatToDatabaseModel(pokemonId : Int) : List<StatEntityDB> {
    return map {
        StatEntityDB(
            baseStat = it.base_stat,
            name = it.statInfo.name,
            pokemonId = pokemonId
        )
    }
}

// TODO: This function is more related to util entities. Better move to util package
private fun extractPokemonNumber(link: String): Int {
    val regex = Regex("/pokemon/(\\d+)/")
    val matchResult = regex.find(link)
    return matchResult?.groupValues?.get(1)?.toIntOrNull() ?: -1
}

// TODO: This function is more related to util entities. Better move to util package
private fun getPokemonImageUrl(number: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${number}.png "
}
