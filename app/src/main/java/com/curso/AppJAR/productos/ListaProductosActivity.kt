package com.curso.AppJAR.productos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R
import com.curso.AppJAR.util.RedUtil
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaProductosActivity : AppCompatActivity() {

    lateinit var listaProductos: ListaProductos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lista_productos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /*
        SI HAY CONEXION A INTERNET
            PIDO EL LISTADO DE PRODUCTOS
            DESPUÉS , MUESTRO EL LISTADO REGISTROS
        SI NO
            MUESTRO UN TOAST O MENSAJE DE ERROR
        * */

        // primero preparo el objeto RetroFit con baseUrl
        // con el prefijo del servidor y el conversor
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val productoService = retrofit.create(ProductoService::class.java)

        if(RedUtil.hayInternet(context=this)) {
            //el bloque dentro de este metodo se ejecuta en segundo plano
            //y si la actividad se cierra, el proceso también se cierra
            Log.d(Constantes.TAG_LOG, "LANZNADO PETICIÓN HTTP 0")
            lifecycleScope.launch {
                // aqui ya tenemos la lista de productos
                Log.d(Constantes.TAG_LOG, "LANZNADO PETICIÓN HTTP 1")
                listaProductos = productoService.obtenerProductos()
                Log.d(Constantes.TAG_LOG, "RESPUESTA RX")
                // ahora es tiempo de recorrerla
                listaProductos.forEach { Log.d(Constantes.TAG_LOG, it.toString()) }
                //TODO HACER UN RECYCLER PARA MOSTRAR LA LISTA DE PRODUCTOS
            }
        } else
        {
                // no hay conexion a Internet tipo Modo avion o sin datos
                val noti = Toast.makeText(this,"Sin conexión a Internet",Toast.LENGTH_LONG)
                noti.show()
            }
            Log.d(Constantes.TAG_LOG, "LANZNADO PETICIÓN HTTP 2")


        }

    }