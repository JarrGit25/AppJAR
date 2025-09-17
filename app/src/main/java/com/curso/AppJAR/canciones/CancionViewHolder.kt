package com.curso.AppJAR.canciones

import com.curso.AppJAR.canciones.Cancion
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.curso.AppJAR.databinding.FilaCancionBinding

/**
 * Esta clase representa el hueco /fila que se recicla y cuyo contenido se actualiza
 * con la información de la cancion que corresponda
 */
class CancionViewHolder(val filaCancion: FilaCancionBinding): RecyclerView.ViewHolder(filaCancion.root) {

    val RUTA_FOTO = "https://i.pinimg.com/736x/03/35/f8/0335f841a01d361c85664c224295d4a1.jpg"

    /**
     * Cargo la info de la cancion en su contenedor
     * @param la canción
     */
    fun rellenarFilaCancion(cancion: Cancion)
    {
        //        artistName: String,
        //        trackName: String,
        //        primaryGenreName: String,
        //        artworkUrl60: String,
        //        previewUrl: String

        this.filaCancion.artista.text = cancion.artistName.toString()
        this.filaCancion.tituloCancion.text = cancion.trackName.toString()
        this.filaCancion.genero.text = cancion.primaryGenreName.toString()
        Glide.with(itemView.context)
            .load(RUTA_FOTO.toUri())
            .into(this.filaCancion.foto)

        // TODO en el id "escucha" cargar la reproduccion en el MEDIA PLAYER
    }

}