package com.example.testphincon.data.remote

import com.example.testphincon.data.form.RenamePokemonForm
import com.example.testphincon.data.model.PokemonDetail
import com.example.testphincon.data.response.CatchPokemonResponse
import com.example.testphincon.data.response.ReleasePokemonResponse
import com.example.testphincon.data.response.RenamePokemonResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String,
    ): PokemonDetail

    @GET("catch")
    suspend fun catchPokemon(): CatchPokemonResponse

    @POST("rename")
    suspend fun renamePokemon(
        @Body form: RenamePokemonForm
    ): RenamePokemonResponse

    @GET("release")
    suspend fun releasePokemon(): ReleasePokemonResponse
}