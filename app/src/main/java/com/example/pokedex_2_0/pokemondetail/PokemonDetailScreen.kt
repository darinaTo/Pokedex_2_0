package com.example.pokedex_2_0.pokemondetail

import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
            pokemonInfo = pokemonInfo,
            pokemonImg = pokemonImg,
            pokemonName = pokemonName,
            dominantColor = dominantColor,
            modifier = Modifier
                .align(Alignment.Center)
        )

    }
}

@Composable
fun PokemonDetailSection(
    pokemonInfo: Pokemon,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = pokemonInfo.name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
        PokemonTypeSection(types = pokemonInfo.types)
        PokemonDetailDataSection(
            pokemonWeight = pokemonInfo.weight,
            pokemonHeight = pokemonInfo.height
        )
    }
}

@Composable
fun PokemonDetailDataItem(
    dataValue: Float,
    dataUnit: String,
    dataCharacteristic: String,
    modifier: Modifier = Modifier
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
            text = dataCharacteristic,
            fontSize = 20.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun PokemonDetailDataSection(
    pokemonWeight: Int,
    pokemonHeight: Int,
    sectionHeight: Dp = 8.dp
) {
    val pokemonWeightInKg = remember {
        round(pokemonWeight * 100f) / 1000f
    }
    val pokemonHeightInM = remember {
        round(pokemonHeight * 100f) / 1000f
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        PokemonDetailDataItem(
            dataValue = pokemonWeightInKg,
            dataUnit = "KG",
            dataCharacteristic = "Weight",
            modifier = Modifier
                .weight(1f)
        )

        Spacer(
            modifier = Modifier
                .size(1.dp, sectionHeight)
        )


        PokemonDetailDataItem(
            dataValue = pokemonHeightInM,
            dataUnit = "M",
            dataCharacteristic = "Height",
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun PokemonTypeSection(types: List<Type>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
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
                    pokemonImg = pokemonImg,
                    pokemonName = pokemonName,
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
                text = pokemonInfo.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier
            )
        }
    }
}

@Composable
fun PokemonTop(
    pokemonImg: String,
    pokemonName: String,
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
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                )
            )
            .background(dominantColor),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 10.dp
                )
        ) {
            Text(
                text = "Pokedex",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            AsyncImage(
                model = pokemonImg,
                contentDescription = pokemonName,
                modifier = Modifier
                    .size(250.dp)
                    .padding(horizontal = 12.dp),
            )
            Text(
                text = "#00${pokemonInfo.id}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

        }
    }
}
