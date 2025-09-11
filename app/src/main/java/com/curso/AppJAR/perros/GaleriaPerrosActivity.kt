package com.curso.AppJAR.perros

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.curso.AppJAR.Usuario
import com.curso.AppJAR.databinding.ActivityGaleriaPerrosBinding

class GaleriaPerrosActivity : AppCompatActivity() {

    lateinit var binding: ActivityGaleriaPerrosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGaleriaPerrosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cargarGaleria()
    }

    private fun cargarGaleria() {

        //recupera la informacion del intent
        val raza:String = intent.getStringExtra("RAZA").toString()
        this.binding.
    }

}