package com.curso.AppJAR.foto

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.R
import com.curso.AppJAR.databinding.ActivityFotoBinding

// la actividad accede a la camará y sacar una foto
// incluye permisos delicados en el Manifest

lateinit var binding: ActivityFotoBinding

class FotoActivity : AppCompatActivity() {
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
        //TODO
    }


}