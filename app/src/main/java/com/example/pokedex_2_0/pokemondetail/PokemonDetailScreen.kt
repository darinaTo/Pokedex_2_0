package com.example.pokedex_2_0.pokemondetail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokedex_2_0.data.models.request.Pokemon
import com.example.pokedex_2_0.data.models.request.pokemondetail.Type
import com.example.pokedex_2_0.ui.theme.LightBlack
import com.example.pokedex_2_0.util.Resource
import com.example.pokedex_2_0.util.parseStatToAbbr
import com.example.pokedex_2_0.util.parseStatToColor
import com.example.pokedex_2_0.util.parseTypeToColor
import kotlin.math.round

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
    pokemonImg: String,
    navController: NavController,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlack),
    ) {
        PokemonBase(
            navController = navController,
            pokemonInfo = pokemonInfo,
            pokemonImg = pokemonImg,
            pokemonName = pokemonName,
            dominantColor = dominantColor,
            modifier = Modifier.align(Alignment.Center)
        )

    }
}

@Composable
fun PokemonDetailSection(
    pokemonInfo: Pokemon, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = pokemonInfo.name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        PokemonTypeSection(types = pokemonInfo.types)
        PokemonDetailDataSection(
            pokemonWeight = pokemonInfo.weight, pokemonHeight = pokemonInfo.height
        )
        Spacer(modifier = Modifier.height(20.dp))
        PokemonBaseStats(pokemonInfo = pokemonInfo)
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float, dataUnit: String, dataCharacteristic: String, modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "$dataValue $dataUnit",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = dataCharacteristic, fontSize = 20.sp, color = Color.Gray
        )
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int, pokemonHeight: Int, sectionHeight: Dp = 8.dp
) {
    val pokemonWeightInKg = remember {
        round(pokemonWeight * 100f) / 1000f
    }
    val pokemonHeightInM = remember {
        round(pokemonHeight * 100f) / 1000f
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "KG",
            dataCharacteristic = "Weight",
            modifier = Modifier.weight(1f)
        )

        Spacer(
            modifier = Modifier.size(1.dp, sectionHeight)
        )


        PokemonDetailDataItem(
            dataValue = pokemonHeightInM,
            dataUnit = "M",
            dataCharacteristic = "Height",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)
    ) {
        for (type in types) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(parseTypeToColor(type))
                    .height(35.dp)
            ) {
                Text(
                    text = type.type.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun PokemonBase(
    navController: NavController,
    pokemonInfo: Resource<Pokemon>,
    pokemonImg: String,
    pokemonName: String,
    dominantColor: Color,
    modifier: Modifier = Modifier,
) {
    when (pokemonInfo) {
        is Resource.Success -> {
            Column {
                PokemonTop(
                    navController = navController,
                    pokemonImg = pokemonImg,
                    dominantColor = dominantColor,
                    pokemonInfo = pokemonInfo.data!!
                )
                Spacer(modifier = Modifier.height(20.dp))

                PokemonDetailSection(
                    pokemonInfo = pokemonInfo.data,
                )

            }
        }

        is Resource.Error -> {
            Text(
                text = pokemonInfo.message!!, color = Color.Red, modifier = modifier
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary, modifier = modifier
            )
        }
    }
}

@Composable
fun PokemonTop(
    navController: NavController,
    pokemonImg: String,
    dominantColor: Color,
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    bottomStart = 50.dp, bottomEnd = 50.dp
                )
            )
            .background(dominantColor),
    ) {
        Column {
            PokemonTopDetail(
                navController = navController,
                pokemonId = pokemonInfo.id
            )
            AsyncImage(
                model = pokemonImg,
                contentDescription = pokemonInfo.name,
                modifier = Modifier
                    .size(250.dp)
                    .padding(horizontal = 20.dp)
                    .align(CenterHorizontally),
            )
        }
    }
}


@Composable
fun PokemonTopDetail(
    navController: NavController,
    pokemonId: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.Top)
                    .clickable {
                        navController.popBackStack()
                    }

            )
            Text(
                text = "Pokedex",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                )
        }

        Text(
            text = if (pokemonId > 10) {
                "#0$pokemonId"
            } else if (pokemonId > 100) {
                "$pokemonId"
            } else {
                "#00$pokemonId"
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.TopEnd),
        )
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    maxState: Int,
    statColor: Color,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / maxState.toFloat()
        } else 0f, label = "animate stat: $statName", animationSpec = tween(
            animDuration, animDelay
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .padding(horizontal = 2.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)


        ) {
            Text(
                text = "${statValue}/$maxState",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
                )
        }
    }
}


@Composable
fun PokemonBaseStats(
    pokemonInfo: Pokemon, animDelayPerItem: Int = 100
) {
    val maxBaseState = remember {
        pokemonInfo.stats.maxOf { it.base_stat }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Base Stats",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(15.dp))

        for (i in pokemonInfo.stats.indices) {
            val stat = pokemonInfo.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.base_stat,
                maxState = maxBaseState,
                statColor = parseStatToColor(stat),
                animDelay = i * animDelayPerItem
            )

            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}