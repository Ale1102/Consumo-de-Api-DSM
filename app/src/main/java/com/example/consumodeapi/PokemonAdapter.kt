package com.example.consumodeapi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consumodeapi.Api.PokemonListResponse
import com.example.consumodeapi.Api.PokemonResult


class PokemonAdapter(private var pokemonList: List<PokemonResult>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    fun updateData(newPhotoList: List<PokemonResult>) {
        pokemonList = newPhotoList
        notifyDataSetChanged() // Notifica al RecyclerView que los datos cambiaron
    }

    // El ViewHolder contiene las referencias a las vistas de cada ítem
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewId: TextView = itemView.findViewById(R.id.textViewId)

        fun bind(pokemon: PokemonResult) {
            // Nombre del Pokémon
            textViewTitle.text = pokemon.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }

            //Número de Pokémon
            textViewId.text = "ID: ${pokemon.getId()}"

            // Carga de Imagen, usamos la URL generada a partir de la ID
            Glide.with(itemView.context)
                .load(pokemon.getImageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(imageViewThumbnail)
        }
    }

    // Crea nuevos ViewHolders cuando no hay suficientes para reciclar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_photo, parent, false)
        return PokemonViewHolder(view)
    }

    // Rellena los datos en un ViewHolder reciclado
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    // Retorna la cantidad total de elementos en la lista
    override fun getItemCount() = pokemonList.size
}