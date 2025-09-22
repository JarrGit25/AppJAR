package com.curso.AppJAR.foto

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R
import com.curso.AppJAR.databinding.ActivityFotoBinding
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

// la actividad accede a la camará y sacar una foto
// incluye permisos delicados en el Manifest

class FotoActivity : AppCompatActivity() {

    lateinit var binding: ActivityFotoBinding
    // defino una variable global para la URI que necesitaremos varias veces
    lateinit var uriFoto: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFotoBinding.inflate((layoutInflater))
        setContentView(binding.root)
    }

    fun tomarFoto(view: View) {
        pedirPermisos()
    }

    private fun pedirPermisos() {
        // Lo Primero es solicitar el permiso a la camara  que ya hemos puesto en el manifest
        //con un numero  aleatorio para
        requestPermissions((arrayOf(android.Manifest.permission.CAMERA)), 500)

        //a la vuelta de la ejecucion de la solicitud de permisos
        // se ejecuta el metodo  onRequestPermissionsResult ()
        // por tanto se debe sobreescribir para programarlo
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // preguntamos si ya tenemos permisos o no con el grantResults
        // como solo hemos pedido 1 permiso accedemos a la posicion 0 del array
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            Log.d(Constantes.ETIQUETA_LOG, "PERMISO DE CAMARA CONCEDIDO")
            lanzarCamara()  // lanzamos la camara
        }
        else{
            Log.d(Constantes.ETIQUETA_LOG, "PERMISO DE CAMARA NO CONCEDIDO")
            Toast.makeText(this, "SIN PERMISO DE CAMARA PARA HACER FOTOS", Toast.LENGTH_LONG)
        }

    }

    private fun lanzarCamara() {
        // 1 creamos un fichero destino para que cree la ruta donde se almacenara el fichero
        this.uriFoto = crearFicheroDestino()  // devolvara la URI
        Log.d(Constantes.ETIQUETA_LOG,"URI FOTO = $uriFoto")
        // 2 lanzo el INTENT
    }

    private fun crearFicheroDestino():Uri {
        val fechaActual = Date() // para guardar la fecha
        val momentoActual = SimpleDateFormat("yyyyMMdd_HHmmss").format(fechaActual)
        val nombreFichero = "FOTO_ADF_$momentoActual"
        // creo la ruta donde se va a generar la foto

        // ruta privada de nuestra app solo visible desde Android Studio
        //val rutaFoto = "${filesDir.path}/$nombreFichero"
        ///data/user/0/com.curso.AppJAR/files/FOTO_ADF_20250922_123007

        // prueba con ruta privada/publica VISIBLE en el explorador de archivos nativo de Android
        //val rutaFoto = "${getExternalFilesDir(null)?.path}/$nombreFichero"
        ///storage/emulated/0/Android/data/com.curso.AppJAR/files/FOTO_ADF_20250922_124618

        // prueba con ruta publica total VISIBLE en el explorador de archivos nativo de Android
        // con este directamente no se puede porque tira una excepcion
        //val rutaFoto = "${Environment.getExternalStorageDirectory()?.path}/$nombreFichero"

        // prueba con ruta de DESCARGAS se podria pero con permisos especiales
        val rutaFoto = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)?.path}/$nombreFichero"
        Log.d(Constantes.ETIQUETA_LOG, "Ruta completa fichero = $rutaFoto")

        // creamos el fichero
        val ficheroFoto = File(rutaFoto)
        // aunque Kotlin no obliga a ello, es conveniente controlar
        // con try Catch por si no pudiese crear la ruta
        ficheroFoto.createNewFile()
        // TODO CREAR RUTA PUBLICA
        return ficheroFoto.toUri()
    }


}