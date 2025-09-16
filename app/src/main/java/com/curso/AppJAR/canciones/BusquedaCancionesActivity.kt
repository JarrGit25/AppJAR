package com.curso.AppJAR.canciones

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.databinding.ActivityBusquedaCancionesBinding
import com.curso.AppJAR.Constantes

class BusquedaCancionesActivity : AppCompatActivity() {

    lateinit var binding: ActivityBusquedaCancionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityBusquedaCancionesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // this.binding.cajaBusqueda.requestFocus()
        this.binding.cajaBusqueda.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(Constantes.ETIQUETA_LOG, "Buscando $query")
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


