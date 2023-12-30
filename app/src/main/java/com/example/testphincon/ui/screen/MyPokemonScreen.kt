package com.example.testphincon.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.testphincon.PokemonViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPokemonScreen(viewModel: PokemonViewModel) {
    val listState = rememberLazyListState()
    val myPokemon by viewModel.myPokemon
    val releasePokemonResult by viewModel.releaseResult
    var openDialog by remember { mutableStateOf(false) }
    var selectedPokemon by remember { mutableStateOf("") }
    var selectedPokemonIdx by remember { mutableStateOf(0) }
    val context = LocalContext.current

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (myPokemon.isEmpty()) {
            Text(text = "Empty")
        } else {
            LazyColumn(Modifier.padding(16.dp), state = listState) {
                itemsIndexed(myPokemon, key = { idx, pokemon ->
                    pokemon.nickname ?: pokemon.name
                }) { idx, pokemon ->
                    Card(
                        onClick = {
                            openDialog = true
                            selectedPokemon = pokemon.nickname!!
                            selectedPokemonIdx = idx
                        },
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
                            Text(text = pokemon.nickname!!.replaceFirstChar {
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

        when {
            openDialog -> {
                AlertDialog(
                    title = { Text(text = "Pokemon Menu") },
                    onDismissRequest = {
                        openDialog = false
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            openDialog = false
                            viewModel.renamePokemon(selectedPokemon)
                        }) {
                            Text(text = "Rename")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            openDialog = false
                            viewModel.releasePokemon(selectedPokemonIdx)
                            if (releasePokemonResult) {
                                Toast.makeText(
                                    context,
                                    "You Successfully Release an Pokemon!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Sorry, You Failed Release an Pokemon!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }) {
                            Text(text = "Release")
                        }
                    })
            }
        }
    }
}