package com.curso.AppJAR.canciones

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.databinding.ActivityBusquedaCancionesBinding
import com.curso.AppJAR.Constantes

/*
PASOS PARA CREAR UN RECYCLERVIEW (MOSTRAR UNA COLECCIÓN/LISTA/TABLA)

-- fase estática/compilación
1) DEFINIR EL RECYCLERVIEW EN EL XML  hecho.
2) CREAR EL LAYOUT/FILA ITEM - ASPECTO hecho.
3) CREAR EL VIEWHOLDER
4) CREAR EL ADAPTER
-- fase dinámica/ejecución
5) OBTENER DATOS (RETROFIT HTTP https://my-json-server.typicode.com/miseon920/json-api/products)
6) INSTANCIAR EL ADAPTER PASÁNDOLE LOS DATOS DEL PUNTO 5
7) ASOCIO EL ADAPTER AL RECYCLER
8) DEFINIMOS UN LAYOUT MANAGER PARA EL RECYCLER
 */

class BusquedaCancionesActivity : AppCompatActivity() {

    //artistName
    //trackName
    //primaryGenreName
    //artworkUrl60
    //previewUrl
    lateinit var binding: ActivityBusquedaCancionesBinding

    private fun mostrarListaCanciones(cadena: String?): Unit {
        Log.d(Constantes.ETIQUETA_LOG, "Mostrando Canciones del artista:  $cadena")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityBusquedaCancionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // this.binding.cajaBusqueda.requestFocus()
        this.binding.cajaBusqueda.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(Constantes.ETIQUETA_LOG, "Buscando $query")

                val textoBusqueda: String = query.toString()
                mostrarListaCanciones(query.toString())

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isEmpty() ?: false)
                {
                    Log.d(Constantes.ETIQUETA_LOG, "CAJA LIMPIA")
                }
                else {
                    Log.d(Constantes.ETIQUETA_LOG, "Cambio $newText")
                }

                return true
            }

        })



    }



    /** override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_canciones, menu)
    val menuItemBusqueda = menu?.findItem(R.id.menuBusqueda)
    val searchView = menuItemBusqueda?.actionView as SearchView
    searchView.queryHint = "Intro nombre o canción"
    return true
    }*/
}


