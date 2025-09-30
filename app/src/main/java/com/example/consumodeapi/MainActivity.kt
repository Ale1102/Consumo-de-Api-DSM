package com.example.consumodeapi
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.consumodeapi.Api.RetrofitClient
import com.example.consumodeapi.Api.PokemonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewStatus: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    // El adaptador debe ser de tipo PokemonAdapter
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de vistas
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        textViewStatus = findViewById(R.id.textViewStatus)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        // Configuración del RecyclerView
        // Cambiado el nombre de la variable de 'photoAdapter' a 'pokemonAdapter' para claridad
        pokemonAdapter = PokemonAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = pokemonAdapter

        // Configuración del deslizar para refrescar
        swipeRefreshLayout.setOnRefreshListener {
            fetchPokemon() // Llamar a la función de carga de datos
        }

        // Carga inicial
        fetchPokemon()
    }

    // Función principal para la carga de datos
    private fun fetchPokemon() {
        // Mostrar indicadores de carga
        if (!swipeRefreshLayout.isRefreshing) {
            showLoadingState()
        }

        // Usamos Coroutines para la petición asíncrona
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getPokemonList()
                }

                if (response.isSuccessful && response.body() != null) {
                    // Extraemos la lista de Pokémon del campo 'results' de la respuesta.
                    val pokemonResults: List<PokemonResult> = response.body()!!.results

                    // Llamamos a la función de éxito con la lista real de Pokémon.
                    showSuccessState(pokemonResults)

                } else {
                    // Manejo de códigos de error HTTP
                    showErrorState("Error en el servidor: ${response.code()}")
                }
            } catch (e: Exception) {
                // Manejo de errores de conexión
                showErrorState("No hay conexión a Internet o error de red.")
                e.printStackTrace()
            } finally {
                // Oculta el indicador de refresco siempre al final
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    //Manejo de Estados de la UI

    private fun showLoadingState() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        textViewStatus.visibility = View.VISIBLE
        textViewStatus.text = "Cargando datos..." // Estado de carga
    }

    // La función debe aceptar una lista de PokemonResult, no PokemonListResponse.
    private fun showSuccessState(pokemonList: List<PokemonResult>) {
        // Debes llamar al método updateData en la instancia de tu adaptador.
        pokemonAdapter.updateData(pokemonList)

        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        textViewStatus.visibility = View.GONE
    }

    private fun showErrorState(message: String) {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        textViewStatus.visibility = View.VISIBLE
        textViewStatus.text = "ERROR: $message \n\nDesliza para reintentar." // Mensaje de error
    }
}