package com.example.testphincon.data.response

import com.example.testphincon.data.model.PokemonDetail

data class PokemonResponse(
    val count: Int = 0,
    val results: List<Pokemon> = emptyList()
) {
    data class Pokemon(
        val name: String = "",
        var detail: PokemonDetail = PokemonDetail()
    )
}
