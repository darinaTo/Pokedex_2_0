package com.example.pokedex_2_0.pokemonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokedex_2_0.data.models.PokemonUiEntity
import com.example.pokedex_2_0.ui.theme.LightBlack
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

// TODO: please see similar comment related to passing navControlled as a parameter
@Composable
fun PokemonListScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightBlack
    ) {
        PokemonList(navController = navController)
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val pokemonList by viewModel.uiState.collectAsStateWithLifecycle() // TODO: collectAsStateWithLifecycle() NICE!

    PokemonGrid(entriesList = pokemonList, navController = navController)
}

@Composable
fun PokemonEntry(
    modifier: Modifier = Modifier,
    navController: NavController,
    entry: PokemonUiEntity,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val defaultColor = MaterialTheme.colorScheme.surface
    var color by remember {
        mutableStateOf(defaultColor)
    }
    val encodedUrl = URLEncoder.encode(entry.imageUrl, StandardCharsets.UTF_8.toString())

    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(color)
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${color.toArgb()}/${entry.pokemonName}/${
                        encodedUrl.substring(
                            0,
                            encodedUrl.lastIndex
                        )
                    }"
                )
            }) {
        Column {
            AsyncImage(
                model = entry.imageUrl,
                contentDescription = entry.pokemonName,
                onSuccess = { success ->
                    val drawable = success.result.drawable
                    viewModel.calcColor(drawable) { dominantColor ->
                        color = dominantColor
                    }
                },
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                text = entry.pokemonName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PokemonGrid(
    entriesList: List<PokemonUiEntity>,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),
        state = rememberLazyGridState(),
        modifier = modifier
    ) {
        items(entriesList) { item ->
            if (item.number >= entriesList.size - 1) {
                viewModel.getPokemon()
            }
            PokemonEntry(navController = navController, entry = item)
        }
    }
}
