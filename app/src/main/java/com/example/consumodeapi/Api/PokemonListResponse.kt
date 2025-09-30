package com.example.consumodeapi.Api

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult> // Lista de Pokémon
)