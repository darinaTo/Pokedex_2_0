package com.example.pokedex_2_0.ui.activities

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.pokedex_2_0.R
import com.example.pokedex_2_0.domain.entities.PokemonUiInfoEntity
import com.example.pokedex_2_0.domain.entities.networkEntities.pokemonDetail.Type
import com.example.pokedex_2_0.ui.theme.Black
import com.example.pokedex_2_0.ui.theme.LightBlack
import com.example.pokedex_2_0.ui.viewmodels.PokemonDetailViewModel
import com.example.pokedex_2_0.utils.parseStatToAbbr
import com.example.pokedex_2_0.utils.parseStatToColor
import com.example.pokedex_2_0.utils.parseTypeToColor
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel = hiltViewModel(),
    onArrowBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pokemonId = uiState.pokemonInfo.id
    val message = uiState.errorMessage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
    ) {
        if (message.isNotEmpty()) {
            ErrorDialog(message = message, modifier = Modifier.align(Center))
        } else {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Center)
                )
            } else {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(
                                text = stringResource(R.string.pokedex),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            )
                        }, colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = uiState.dominantColor,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        ), navigationIcon = {
                            IconButton(onClick = { onArrowBackClick() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "back navigate",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(25.dp)

                                )
                            }
                        },
                            actions = {
                                Text(
                                    text = if (pokemonId in 10..100) {
                                        stringResource(R.string.id_0, pokemonId)
                                    } else if (pokemonId <= 10) {
                                        stringResource(R.string.id_00, pokemonId)
                                    } else {
                                        stringResource(R.string.id, pokemonId)
                                    },
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                )
                            }
                        )
                    }
                ) { innerPadding ->
                    PokemonTop(
                        modifier = Modifier.padding(innerPadding),
                        dominantColor = uiState.dominantColor,
                        pokemonInfoApiEntityInfo = uiState.pokemonInfo
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonTop(
    dominantColor: Color,
    pokemonInfoApiEntityInfo: PokemonUiInfoEntity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(LightBlack)
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
            AsyncImage(
                model = pokemonInfoApiEntityInfo.image,
                contentDescription = pokemonInfoApiEntityInfo.name,
                modifier = Modifier
                    .size(250.dp)
                    .padding(horizontal = 20.dp)
                    .align(Center),
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        PokemonDetailSection(
            pokemonInfoApiEntityInfo = pokemonInfoApiEntityInfo,
        )
    }
}


@Composable
fun PokemonDetailSection(
    pokemonInfoApiEntityInfo: PokemonUiInfoEntity, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = CenterHorizontally, modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = pokemonInfoApiEntityInfo.name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
        PokemonTypeSection(types = pokemonInfoApiEntityInfo.types)
        PokemonDetailDataSection(
            pokemonWeight = pokemonInfoApiEntityInfo.weight,
            pokemonHeight = pokemonInfoApiEntityInfo.height
        )
        Spacer(modifier = Modifier.height(20.dp))
        PokemonBaseStats(pokemonInfoApiEntityInfo = pokemonInfoApiEntityInfo)
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float, dataUnit: String, dataCharacteristic: String, modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = CenterHorizontally,
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
    pokemonWeight: Float, pokemonHeight: Float, sectionHeight: Dp = 8.dp
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeight,
            dataUnit = stringResource(R.string.kg),
            dataCharacteristic = stringResource(R.string.weight),
            modifier = Modifier.weight(1f)
        )

        Spacer(
            modifier = Modifier.size(1.dp, sectionHeight)
        )


        PokemonDetailDataItem(
            dataValue = pokemonHeight,
            dataUnit = stringResource(R.string.m),
            dataCharacteristic = stringResource(R.string.height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(types.size),
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        items(types) { type ->
            Box(
                contentAlignment = Center,
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .padding(13.dp)
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
fun PokemonStat(
    statName: String,
    statValue: Int,
    maxValue: Int,
    statColor: Color,
    animDuration: Int = 500,
    animDelay: Int = 0,
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / maxValue.toFloat()
        } else 0f, label = "animate stat: $statName", animationSpec = tween(
            animDuration, animDelay
        )
    )
    val textShow = remember {
        statValue * 100 / maxValue.toFloat()
    }

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = statName,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(0.17f),
            textAlign = TextAlign.Center
        )
        PokemonStatPercent(
            curPercent = curPercent,
            statColor = statColor,
            textShow = textShow,
            maxValue = maxValue,
            statValue = statValue,
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
fun PokemonStatPercent(
    curPercent: State<Float>,
    statColor: Color,
    textShow: Float,
    maxValue: Int,
    statValue: Int,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .height(23.dp)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp, vertical = 1.dp)
        ) {
            if (textShow.absoluteValue.toInt() > 20) {
                Text(
                    text = stringResource(R.string.percent, statValue, maxValue),
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
        if (textShow.absoluteValue.toInt() <= 20) {
            Text(
                text = stringResource(R.string.percent, statValue, maxValue),
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = (curPercent.value + 8).dp)
            )
        }
    }
}

@Composable
fun PokemonBaseStats(
    pokemonInfoApiEntityInfo: PokemonUiInfoEntity, animDelayPerItem: Int = 100
) {
    val maxBaseState = remember {
        pokemonInfoApiEntityInfo.stats.maxOf { it.base_stat }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.base_stats),
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyColumn {
            items(pokemonInfoApiEntityInfo.stats) { item ->
                val index = pokemonInfoApiEntityInfo.stats.indexOf(item)
                if (parseStatToAbbr(item).isNotEmpty()) {
                    PokemonStat(
                        statName = parseStatToAbbr(item),
                        statValue = item.base_stat,
                        maxValue = maxBaseState,
                        statColor = parseStatToColor(item),
                        animDelay = index * animDelayPerItem
                    )
                }
            }
        }
    }
}