package com.example.testphincon.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.testphincon.PokemonViewModel
import java.util.Locale

@Composable
fun DetailPokemonScreen(viewModel: PokemonViewModel, pokemonName: String) {
    val pokemon by viewModel.pokemon
    val captureResult by viewModel.captureResult
    val myPokemon by viewModel.myPokemon
    val scrollState = rememberScrollState()
    val openDialog = remember { mutableStateOf(false) }
    val openDialogNickname = remember { mutableStateOf(false) }
    var pokemonNickName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = when (pokemon.types.firstOrNull()?.type?.name ?: "") {
                        "grass" -> Color(0xFF64e880)
                        "fire" -> Color(0xFFe87364)
                        "bug" -> Color(0xFFe8bc64)
                        "normal" -> Color(0xFFe8db64)
                        else -> Color(0xFF64dfe8)
                    }
                )
            ) {
                Column(
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = pokemon.sprites.other.officialArtwork.front_default,
                        contentDescription = pokemon.name
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Text(
                    text = "Pokemon Name:",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(text = pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                })
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Types:", fontWeight = FontWeight.Bold)
            pokemon.types.forEach {
                Text(text = it.type.name)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Abilities:", fontWeight = FontWeight.Bold)
            pokemon.abilities.forEach {
                Text(text = it.ability.name)
            }
            Text(text = "Moves:", fontWeight = FontWeight.Bold)
            pokemon.moves.forEachIndexed { index, moves ->
                Text(text = "${index + 1}. ${moves.move.name}")
            }
        }
        FloatingActionButton(
            onClick = {
                openDialog.value = true
                viewModel.catchPokemon()
            }, modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
                .align(Alignment.BottomEnd), shape = CircleShape
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Catch Pokemon")
        }
        when {
            openDialog.value -> {
                AlertDialog(
                    title = { Text(text = "Capture Result") },
                    text = { Text(text = if (captureResult.captured == 0) "You Failed captured $pokemonName" else "You Successfully captured $pokemonName") },
                    onDismissRequest = { openDialog.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            openDialog.value = false
                            openDialogNickname.value = captureResult.captured == 1
                        }) {
                            Text(text = "OK")
                        }
                    })
            }

            openDialogNickname.value -> {
                AlertDialog(
                    title = { Text(text = "Add Nickname") },
                    text = {
                        TextField(
                            value = pokemonNickName,
                            onValueChange = { pokemonNickName = it })
                    },
                    onDismissRequest = { },
                    confirmButton = {
                        TextButton(onClick = {
                            openDialogNickname.value = pokemonNickName.isEmpty()
                            var pokemons = myPokemon.toTypedArray()
                            pokemon.nickname = pokemonNickName
                            pokemons = pokemons.plus(pokemon)
                            viewModel.addToMyPokemon(pokemons.asList())
                            Toast.makeText(
                                context,
                                "$pokemonNickName has been added to your Pokemon List!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }) {
                            Text(text = "OK")
                        }
                    })
            }
        }
    }

    DisposableEffect(key1 = Unit) {
        viewModel.getPokemonDetail(pokemonName)
        onDispose { }
    }
}