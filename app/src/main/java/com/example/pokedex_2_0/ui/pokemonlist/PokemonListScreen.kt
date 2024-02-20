package com.example.pokedex_2_0.ui.pokemonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.pokedex_2_0.data.models.PokemonUiEntity
import com.example.pokedex_2_0.ui.theme.LightBlack
import com.example.pokedex_2_0.util.Constants.POKEMON_DETAIL_ROUTE
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    onScreenTab: (String) -> Unit,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Pokedex",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            )
        )
    }) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            color = LightBlack
        ) {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            PokemonGrid(entriesList = uiState.pokemon, onScreenTab = onScreenTab)
        }
    }
}


@Composable
fun PokemonGrid(
    entriesList: List<PokemonUiEntity>,
    onScreenTab: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(13.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp),
        state = rememberLazyGridState(),
        modifier = modifier.padding(top = 15.dp)
    ) {
        items(entriesList) { item ->
            if (item.number >= entriesList.size - 1) {
                viewModel.getPokemon()
            }
            PokemonEntry(onScreenTab = onScreenTab, entry = item)
        }
    }
}

@Composable
fun PokemonEntry(
    modifier: Modifier = Modifier,
    onScreenTab: (String) -> Unit,
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
                onScreenTab(
                    "${POKEMON_DETAIL_ROUTE}/${color.toArgb()}/${entry.pokemonName}/${
                        encodedUrl.substring(
                            0,
                            encodedUrl.lastIndex
                        )
                    }"
                )
            }) {
        var isLoading by remember { mutableStateOf(true) }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Column {
            AsyncImage(
                model = entry.imageUrl,
                contentDescription = entry.pokemonName,
                onSuccess = { success ->
                    val drawable = success.result.drawable
                    viewModel.calcColor(drawable) { dominantColor ->
                        color = dominantColor
                    }
                    isLoading = false

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
