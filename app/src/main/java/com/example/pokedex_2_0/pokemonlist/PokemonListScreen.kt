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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.request.ImageRequest
import com.example.pokedex_2_0.data.models.PokemonUI
import com.example.pokedex_2_0.data.models.PokemonViewModel
import com.google.accompanist.coil.CoilImage

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
fun PokemonEntry(
    modifier: Modifier = Modifier,
    navController: NavController,
    entry: PokemonUI,
    viewModel: PokemonViewModel = PokemonViewModel()
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
            CoilImage(
                request = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageUrl)
                    .target {
                        viewModel.calcColor(it) { dominantColor ->
                            color = dominantColor
                        }
                    }
                    .build(),
                contentDescription = entry.pokemonName,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
            ) {

            }
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
    entriesList: List<PokemonUI>,
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