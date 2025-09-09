package com.curso.AppJAR.util

import android.content.Context
import android.net.ConnectivityManager

/*
Para comprobar el estado de la conexion a internet
* */
object RedUtil {
    fun hayInternet (context: Context): Boolean {
        var hay = false

        // retorna si hay internet o no
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        hay = (cm.activeNetwork!=null)

        return hay
    }
}