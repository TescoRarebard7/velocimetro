package com.example.velocimetro

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.velocimetro.databinding.ActivityVelocimetroBinding

class Velocimetro : AppCompatActivity() {
    private lateinit var binding: ActivityVelocimetroBinding
    private var isKm = true
    private lateinit var locationManager: LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVelocimetroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        startLocation()

    }

    private fun setup() {
        binding.change.setOnClickListener {
            isKm = !isKm
            updateSpeedtext()
        }
    }

    private fun updateSpeedtext() {
        binding.change.text = if(isKm) "cambiar a Mi/h" else "cambiar a Km/h"
    }

    @SuppressLint("MissingPermission")
    private fun startLocation() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    val velocidadEnMetrosPorSegundo = location.speed
                    if (isKm){
                        val velocidadEnMetrosPorHora = velocidadEnMetrosPorSegundo * 3.6
                        binding.speed.text = "${String.format("%.2f", velocidadEnMetrosPorHora)} Km/h"
                    }else{
                        val velocidadEnMillasPorhora = velocidadEnMetrosPorSegundo * 2.23694
                        binding.speed.text = "${String.format("%.2f", velocidadEnMillasPorhora)} Mi/h"
                    }

                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        startLocation()
    }

}