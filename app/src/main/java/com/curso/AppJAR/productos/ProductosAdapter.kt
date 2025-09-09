package com.curso.AppJAR.productos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.R

// recibe la lista de productos
class ProductosAdapter (var listaProductos: ListaProductos): RecyclerView.Adapter<ProductoViewHolder> (){

    //"inflamos la fila"
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val filaProducto = layoutInflater.inflate(R.layout.fila_producto, parent, false)
        val productoViewHolder = ProductoViewHolder(filaProducto)
        return productoViewHolder

    }

    //" rellenamos la fila"
    override fun onBindViewHolder(
        holder: ProductoViewHolder,
        position: Int
    ) {
        val productoActual = this.listaProductos.get(position)
        holder.rellenarFilaProducto(productoActual)
    }

    //gracias a este métod o, el Recycler sabe cuántos items tiene que pintar
    override fun getItemCount(): Int {
        return this.listaProductos.size
    }
}