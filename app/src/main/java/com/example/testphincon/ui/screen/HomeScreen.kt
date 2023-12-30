package com.example.testphincon.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.testphincon.PokemonViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: PokemonViewModel, navController: NavHostController) {
    val listState = rememberLazyListState()
    val pokemons by viewModel.pokemons
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            IconButton(onClick = { navController.navigate("myPokemon") }) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "My Pokemon")
            }
        }
        if (pokemons.isEmpty()) {
            Text(text = "Loading.....")
        } else {
            LazyColumn(Modifier.padding(16.dp), state = listState) {
                items(pokemons, key = { pokemon ->
                    pokemon.name
                }) { pokemon ->
                    Card(
                        onClick = { navController.navigate("detail/${pokemon.name}") },
                        colors = CardDefaults.cardColors(
                            containerColor = when (pokemon.types[0].type.name) {
                                "grass" -> Color(0xFF64e880)
                                "fire" -> Color(0xFFe87364)
                                "bug" -> Color(0xFFe8bc64)
                                "normal" -> Color(0xFFe8db64)
                                else -> Color(0xFF64dfe8)
                            },
                            contentColor = Color.White
                        )
                    ) {
                        Column(
                            Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = pokemon.sprites.other.officialArtwork.front_default,
                                contentDescription = pokemon.name
                            )
                            Text(text = pokemon.name.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.ROOT
                                ) else it.toString()
                            }, textAlign = TextAlign.Center)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}