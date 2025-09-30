package com.example.consumodeapi.Api
data class PokemonResult(
    val name: String, // Usado para el título
    val url: String // Usado para obtener la ID del Pokémon
) {
    // Función de ayuda para extraer la ID numérica del Pokémon de la URL
    fun getId(): Int {
        // La URL es como: https://pokeapi.co/api/v2/pokemon/1/
        // Esto toma el número (1) de la URL
        val parts = url.split("/")
        return parts[parts.size - 2].toIntOrNull() ?: 0
    }

    // Función de ayuda para obtener la URL de la imagen del Pokémon
    fun getImageUrl(): String {
        val id = getId()
        // Usamos el servicio oficial de GitHub para las imágenes
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    }
}