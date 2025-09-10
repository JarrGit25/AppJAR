package com.curso.AppJAR.productos

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R
import com.curso.AppJAR.databinding.ActivityListaProductosBinding
import com.curso.AppJAR.util.RedUtil
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt
import kotlin.system.measureNanoTime


class ListaProductosActivity : AppCompatActivity() {

    lateinit var listaProductos: ListaProductos
    // para instanciar el Adapter
    lateinit var productosAdapter: ProductosAdapter

    lateinit var binding: ActivityListaProductosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        //instancio
        val productoService = retrofit.create(ProductoService::class.java)

        if(RedUtil.hayInternet(this)) {
            //el bloque dentro de este metodo se ejecuta en segundo plano
            //y si la actividad se cierra, el proceso también se cierra

            //probar si hay wifi
            val haywifi = RedUtil.hayWifi(this)

            Log.d(Constantes.TAG_LOG, "LANZANDO PETICIÓN HTTP 0")
            lifecycleScope.launch {
                // aqui ya tenemos la lista de productos
                Log.d(Constantes.TAG_LOG, "LANZANDO PETICIÓN HTTP 1")


                Log.d(Constantes.TAG_LOG, "LANZANDO PETICIÓN HTTP 1")

                listaProductos = productoService.obtenerProductos()
                Log.d(Constantes.TAG_LOG, "RESPUESTA RX")

                //ocultar progress bar y la vaciamos del XML con GONE
                this@ListaProductosActivity.binding.barraProgreso.visibility= View.GONE

                // ahora es tiempo de recorrerla
                listaProductos.forEach { Log.d(Constantes.TAG_LOG, it.toString()) }

                mostrarListaProductos(listaProductos)

                dibujarSlider()

                //HACER UN RECYCLER PARA MOSTRAR LA LISTA DE PRODUCTOS
                /*
                PASOS PARA CREAR UN RECYCLERVIEW (MOSTRAR UNA COLECCIÓN/LISTA/TABLA)

                -- fase estática/compilación
                1) DEFINIR EL RECYCLERVIEW EN EL XML
                2) CREAR EL LAYOUT/FILA ITEM - ASPECTO
                3) CREAR EL VIEWHOLDER
                4) CREAR EL ADAPTER
                -- fase dinámica/ejecución
                5) OBTENER DATOS (RETROFIT HTTP https://my-json-server.typicode.com/miseon920/json-api/products)
                6) INSTANCIAR EL ADAPTER PASÁNDOLE LOS DATOS DEL PUNTO 5
                7) ASOCIO EL ADAPTER AL RECYCLER
                8) DEFINIMOS UN LAYOUT MANAGER PARA EL RECYCLER
                */
            }
        } else
        {
                // no hay conexion a Internet tipo Modo avion o sin datos
                val noti = Toast.makeText(this,"Sin conexión a Internet",Toast.LENGTH_LONG)
                noti.show()
            }
            Log.d(Constantes.TAG_LOG, "LANZNADO PETICIÓN HTTP 2")


        }

    private fun dibujarSlider(){
        // pone el Slider visible
        this.binding.sliderPrecio.visibility = View.VISIBLE

        //obtengo de la lista el producto mas caro
        // previamente convirtiendo a float porque viene como string
        val productoMasCaro = this.listaProductos.maxBy { p : ListaProductosItem -> p.price.toFloat() }

        //obtengo de la lista el producto mas economico
        val productoMasEconomico = this.listaProductos.minBy { p : ListaProductosItem -> p.price.toFloat() }

        //obtengo de la lista el precio medio de los productos
        // nos quedamos con los precios en la lista y luego calculo la media
        var precioMedio:Double = 0.0
        val tlambdas =  measureNanoTime {
            precioMedio = this.listaProductos.map { p->p.price.toFloat() }.average()
        }

        var tforClasico = measureNanoTime {
            var sumaPrecio:Float = 0f
            for (p in listaProductos.indices)
            {
                sumaPrecio = sumaPrecio + listaProductos[p].price.toFloat()
            }
            val media = sumaPrecio/listaProductos.size

        }
        Log.d(Constantes.TAG_LOG, "Tiempo lambdas ${tlambdas} vs Tiempo clásico ${tforClasico}")

        this.binding.precioMasBarato.text = productoMasEconomico.price
        this.binding.precioMasCaro.text = productoMasCaro.price
        this.binding.precioMedio.text = precioMedio.toString()


        // inicia el Slider
        this.binding.sliderPrecio.visibility = View.VISIBLE
        //El valor minimo hasta donde iria el Slider
        this.binding.sliderPrecio.valueFrom = productoMasEconomico.price.toFloat()
        // El valor maximo hasta donde iria el Slider
        this.binding.sliderPrecio.valueTo = productoMasCaro.price.toFloat()
        //donde aparece la barra
        this.binding.sliderPrecio.value = productoMasCaro.price.toFloat()

        // le da un formato
        this.binding.sliderPrecio.setLabelFormatter{ "${it.roundToInt()} precio máx" }

        // creamos el listener del Slider al cambiar
        this.binding.sliderPrecio.addOnChangeListener { slider, value, fromUser ->

            Log.d(Constantes.TAG_LOG, "Valor actual ${value} del usuario ${fromUser}")
            var listaProductosFiltrados = ListaProductos()
            this.listaProductos.filter { producto -> if (producto.price.toFloat()<=value) {true} else {false} }.toCollection(listaProductosFiltrados)
            this.productosAdapter.listaProductos = listaProductosFiltrados
            this.productosAdapter.notifyDataSetChanged()//"Emite una señal y RecyclerView se repinta"
        }


    }

    private fun mostrarListaProductos(listaProductos: ListaProductos) {
        //6) INstanciando el adapter pasando la lista
        this.productosAdapter = ProductosAdapter(listaProductos)
        //7) ASOCIO EL ADAPTER AL RECYCLER
        binding.recViewProductos.adapter = this.productosAdapter
        //8) DEFINIMOS UN LAYOUT MANAGER PARA EL RECYCLER
        this.binding.recViewProductos.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false  )
    }

}