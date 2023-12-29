package com.example.testphincon

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String,
    ): PokemonDetail
}