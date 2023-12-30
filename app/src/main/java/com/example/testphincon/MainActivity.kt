package com.example.testphincon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testphincon.ui.screen.DetailPokemonScreen
import com.example.testphincon.ui.screen.HomeScreen
import com.example.testphincon.ui.screen.MyPokemonScreen
import com.example.testphincon.ui.screen.SplashScreen
import com.example.testphincon.ui.theme.TestPhinconTheme

class MainActivity : ComponentActivity() {
    private val pokemonVM: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestPhinconTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "splash") {
            composable("splash") {
                SplashScreen(navController)
            }
            composable("home") {
                HomeScreen(viewModel = pokemonVM, navController)
            }
            composable("detail/{pokemon_name}") {
                DetailPokemonScreen(
                    viewModel = pokemonVM,
                    it.arguments?.getString("pokemon_name") ?: ""
                )
            }
            composable("myPokemon") {
                MyPokemonScreen(viewModel = pokemonVM)
            }
        }
    }
}