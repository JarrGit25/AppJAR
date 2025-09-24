package com.curso.AppJAR.receptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.curso.AppJAR.Constantes
import com.curso.AppJAR.MainMenuActivity

class inicioMovilReceiver : BroadcastReceiver() {


    // necesita un permiso
    // permite que nuestra app este informada del inicio del dispositivo
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        //TODO("inicioMovilReceiver.onReceive() is not implemented")
        Log.d(Constantes.ETIQUETA_LOG,"En inicioMovilReceiver")
        context.startActivity(Intent(context, MainMenuActivity::class.java))
    }
}