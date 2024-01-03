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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokedex_2_0.data.models.PokemonEntry
import com.example.pokedex_2_0.data.models.PokemonViewModel

@Composable
fun PokemonListScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {

        }
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonViewModel = viewModel()
) {
    val pokemonList by viewModel.pokemonList.collectAsStateWithLifecycle()
    val status = viewModel.status

    PokemonGrid(entriesList = pokemonList, navController = navController)
}

@Composable
fun PokemonEntry(
    modifier: Modifier = Modifier,
    navController: NavController,
    entry: PokemonEntry,
    viewModel: PokemonViewModel = viewModel()
) {
    val defaultColor = MaterialTheme.colorScheme.surface
    var color by remember {
        mutableStateOf(defaultColor)
    }

    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(color)
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${color.toArgb()}/${entry.pokemonName}"
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
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PokemonGrid(
    entriesList: List<PokemonEntry>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),
        modifier = modifier
    ) {
        items(entriesList) { item ->
            PokemonEntry(navController = navController, entry = item)
        }
    }
}

@Preview
@Composable
fun PokemonListScreenPreview() {

}