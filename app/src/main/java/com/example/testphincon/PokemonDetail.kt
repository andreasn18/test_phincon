package com.example.testphincon

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    val name: String = "",
    var nickname: String? = null,
    val abilities: List<Abilities> = emptyList(),
    val sprites: Sprites = Sprites(),
    val types: List<Types> = listOf(),
    val moves: List<Moves> = listOf()
) {

    data class Abilities(
        val ability: Ability,
        val is_hidden: Boolean,
        val slot: Int
    )

    data class Sprites(
        val other: Other = Other()
    ) {
        data class Other(
            @SerializedName("official-artwork")
            val officialArtwork: OfficialArtwork = OfficialArtwork()
        ) {
            data class OfficialArtwork(
                val front_default: String = ""
            )
        }
    }

    data class Types(
        val type: Ability = Ability()
    )

    data class Moves(
        val move: Ability = Ability()
    )

    data class Ability(
        val name: String = "",
        val url: String = ""
    )
}
