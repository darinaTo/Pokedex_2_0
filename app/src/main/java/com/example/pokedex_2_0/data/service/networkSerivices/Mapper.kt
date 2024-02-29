package com.example.pokedex_2_0.data.service.networkSerivices

import com.example.pokedex_2_0.domain.entities.PokemonUiEntity
import com.example.pokedex_2_0.domain.entities.PokemonUiInfoEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.PokemonInfoDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.PokemonInfoFullInfo
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.StatDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonDetail.TypeDbEntity
import com.example.pokedex_2_0.domain.entities.dbEntities.pokemonList.PokemonDbEntity
import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail.PokemonInfoApiResponse
import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail.Stat
import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail.StatX
import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail.Type
import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail.TypeX
import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonList.PokemonApiResponse
import com.example.pokedex_2_0.utils.covertValue
import com.example.pokedex_2_0.utils.extractPokemonNumber
import com.example.pokedex_2_0.utils.getPokemonImageUrl

fun PokemonApiResponse.mapToDatabaseModel(): List<PokemonDbEntity> {
    return this.pokemonApiEntities.map { pokemon ->
        val number = extractPokemonNumber(pokemon.url)
        val url = getPokemonImageUrl(number)
        PokemonDbEntity(
            id = number,
            image = url,
            name = pokemon.name,
        )
    }
}

fun List<PokemonDbEntity>.mapToUiEntity(): List<PokemonUiEntity> {
    return map {
        PokemonUiEntity(
            pokemonName = it.name,
            imageUrl = it.image,
            number = it.id
        )
    }
}


fun PokemonInfoApiResponse.mapToDatabaseModel(): PokemonInfoDbEntity {
    return PokemonInfoDbEntity(
        id = this.id,
        name = this.name,
        weight = this.weight,
        height = this.height,
        image = getPokemonImageUrl(id)
    )
}

fun PokemonInfoFullInfo.mapToUiEntity(): PokemonUiInfoEntity {
    return PokemonUiInfoEntity(
        id = this.pokemonEntity.id,
        name = this.pokemonEntity.name,
        height = covertValue(this.pokemonEntity.height),
        weight = covertValue(this.pokemonEntity.weight),
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
        },
        image = this.pokemonEntity.image
    )

}

fun List<Type>.mapTypeToDatabaseModel(pokemonId: Int): List<TypeDbEntity> {
    return map {
        TypeDbEntity(
            name = it.type.name,
            pokemonId = pokemonId
        )
    }
}

fun List<Stat>.mapStatToDatabaseModel(pokemonId: Int): List<StatDbEntity> {
    return map {
        StatDbEntity(
            baseStat = it.base_stat,
            name = it.statInfo.name,
            pokemonId = pokemonId
        )
    }
}
