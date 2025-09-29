package com.example.consumodeapi.Api

// PokemonListResponse.kt
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult> // Aquí está la lista de Pokémon
)