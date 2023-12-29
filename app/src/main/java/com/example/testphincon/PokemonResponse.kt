package com.example.testphincon

data class PokemonResponse(
    val count: Int = 0,
    val results: List<Pokemon> = emptyList()
) {
    data class Pokemon(
        val name: String = "",
        var detail: PokemonDetail = PokemonDetail()
    )
}
