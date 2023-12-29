package com.example.testphincon

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    private val apiService = RetrofitInstance.api
    val pokemons: MutableState<List<PokemonDetail>> = mutableStateOf(listOf())
    private var pokemonsRaw = arrayListOf<PokemonDetail>()
    private val maxListLength = 21

    fun getPokemonList() {
        viewModelScope.launch {
            try {
                repeat(maxListLength) {
                    val response = apiService.getPokemonDetail(it.plus(1).toString())
                    if (pokemonsRaw.count() >= 21)
                        pokemonsRaw.clear()
                    pokemonsRaw.add(response)
                }
                pokemons.value = pokemonsRaw
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
            }
        }
    }

    fun getPokemonDetail(index: Int, name: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getPokemonDetail(name)
//                pokemonsRaw[index].detail = response
//                pokemons.value = pokemonsRaw
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
            }
        }
    }
}