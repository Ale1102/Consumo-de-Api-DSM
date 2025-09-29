package com.example.consumodeapi.Api
// ApiService.kt
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // Pide la lista de Pokémon. Usamos @Query para limitar la cantidad a 20
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int = 60): Response<PokemonListResponse>
}