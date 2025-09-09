package com.curso.AppJAR.productos

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.curso.AppJAR.R

/**
 * Esta clase representa el hueco /fila que se recicla y cuyo contenido se actualiza
 * con la información del usuario que toque
 */
class ProductoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
    //definimos un atributo para ligarlo a cada columna de la fila

    val tvId: TextView = itemView.findViewById<TextView>(R.id.id)
    val tvname: TextView = itemView.findViewById<TextView>(R.id.name)
    val tvPrice: TextView = itemView.findViewById<TextView>(R.id.price)
    val imvImg: ImageView = itemView.findViewById<ImageView>(R.id.imageUrl)

    /**
     * Cargamos la información del usuario en su contenedor
     * @param el usuario corriente/actual
     */
    fun rellenarFilaProducto (producto: ListaProductosItem)
    {
        this.tvId.text = producto.id.toString()
        this.tvname.text = producto.name
        this.tvPrice.text = producto.price.toString()
        // poner la imagen
        Glide.with(this.itemView.context)
            .load(producto.imageUrl.toUri())
            .into(imvImg)
    }
}