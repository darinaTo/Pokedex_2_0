package com.example.pokedex_2_0.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pokedex_2_0.data.models.request.Pokemon
import com.example.pokedex_2_0.util.Resource

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
/*
    pokemonImg : String,
*/
    navController: NavController,
    viewModel: PokemonDetailViewModel = viewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value
    Box(
        contentAlignment =  Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
    ) {
      if (pokemonInfo is Resource.Success) {

      }
    }
}


@Composable
fun PokemonTop(
    pokemonInfo: Resource<Pokemon>,
    modifier: Modifier = Modifier
) {
 when(pokemonInfo) {
     is Resource.Success -> {

     }
     is  Resource.Error -> {
       Text(
           text = pokemonInfo.message!!,
           color = Color.Red,
           modifier = modifier
       )
     }
     is Resource.Loading -> {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
        )
     }
 }
}
