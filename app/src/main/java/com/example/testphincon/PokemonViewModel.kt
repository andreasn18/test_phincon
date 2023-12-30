package com.example.testphincon

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    private val apiService = RetrofitInstance.api
    private val apiService2 = RetrofitInstance.api2
    val pokemons: MutableState<List<PokemonDetail>> = mutableStateOf(emptyList())
    val pokemon: MutableState<PokemonDetail> = mutableStateOf(PokemonDetail())
    val captureResult = mutableStateOf(CatchPokemonResponse())
    val releaseResult = mutableStateOf(false)

    val myPokemon: MutableState<List<PokemonDetail>> = mutableStateOf(emptyList())

    private var pokemonsRaw = arrayListOf<PokemonDetail>()
    private val maxListLength = 21

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        viewModelScope.launch {
            try {
                repeat(maxListLength) {
                    val response = apiService.getPokemonDetail(it.plus(1).toString())
                    if (pokemonsRaw.count() > 21)
                        pokemonsRaw.clear()
                    pokemonsRaw.add(response)
                }
                pokemons.value = pokemonsRaw
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
            }
        }
    }

    fun getPokemonDetail(name: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getPokemonDetail(name)
                pokemon.value = response
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
            }
        }
    }

    fun catchPokemon() {
        viewModelScope.launch {
            try {
                val response = apiService2.catchPokemon()
                captureResult.value = response
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
            }
        }
    }

    fun addToMyPokemon(newList: List<PokemonDetail>) {
        myPokemon.value = newList
    }

    fun renamePokemon(name: String) {
        viewModelScope.launch {
            try {
                val response = apiService2.renamePokemon(RenamePokemonForm(name))
                val myPokemonList = myPokemon.value.filter { it.nickname == name }
                myPokemonList.first().nickname = response.newName
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
            }
        }
    }

    fun releasePokemon(idx: Int) {
        viewModelScope.launch {
            try {
                val primeNumbers = listOf(2,3,5,7)
                val response = apiService2.releasePokemon()
                if (response.releaseNumber in primeNumbers) {
                    releaseResult.value = true
                    val dummyList = myPokemon.value.filterIndexed { index, pokemonDetail ->
                        index != idx
                    }
                    myPokemon.value = dummyList
                }
                else {
                    releaseResult.value = false
                }
            } catch (e: Exception) {
                Log.e("API Error", e.message.toString())
            }
        }
    }
}