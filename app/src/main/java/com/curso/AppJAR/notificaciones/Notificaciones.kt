package com.curso.AppJAR.notificaciones

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.curso.AppJAR.MainMenuActivity
import com.curso.AppJAR.R

object Notificaciones {

    // dos atributos necesarios para definir al canal
    val NOTIFICATION_CHANNEL_ID = "UNO"
    val NOTIFICATION_CHANNEL_NAME = "CANAL_ADF"

    private fun crearCanalNotificacion (context: Context):NotificationChannel?
    {
        return null
    }

    // recibe el contexto
    fun lanzarNotificacion(context:Context) {

        // a traves del contexto accedo al servicio de NOTIFICACIONES del sistema
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //creo el canal
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            var notificationChannel = crearCanalNotificacion(context)
            notificationManager.createNotificationChannel(notificationChannel!!)
        }

        //CREAMOS LA NOTIFICACIÓN
        var nb = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.outline_notifications_active_24)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.emoticono_risa))
            .setContentTitle("BUENOS DÍAS")
            .setSubText("aviso")
            .setContentText("Vamos a ver fotos de perros")
            .setAutoCancel(true)//es para que cuando toque la noti, desaparezca
            .setDefaults(Notification.DEFAULT_ALL)

        // lanzamos un intent para llamar a otra actividad
        val intentDestino = Intent(context, MainMenuActivity::class.java)
        // que el intent se lance solo una vez y no se pueda modificar
        // permite lanzar el intent como si estuviese dentro de mi app
        // y este seria un intent securizado
        val pendingIntent = PendingIntent.getActivity(context, 100,
            intentDestino,PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        nb.setContentIntent(pendingIntent) // asocio el intent a la notificacion
        val notificacion = nb.build() // devuelve la notificacion ya construida

        // lanza la notificacion necesita añadir permisos POST_NOTIFICATIONS
        notificationManager.notify(500, notificacion)
    }

}