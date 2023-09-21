package com.example.velocimetro

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.velocimetro.databinding.ActivityHaversineBinding
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class HaversineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHaversineBinding
    private lateinit var locationManager: LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHaversineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        startLocation()
    }

    private fun setup() {

        binding.saveDistance1.setOnClickListener {
            val lat = binding.lat.text.toString()
            val lon = binding.longuitud.text.toString()

            binding.ubi1Lat.text = lat
            binding.ubi1Long.text = lon
        }

        binding.saveDistance2.setOnClickListener {
            val lat = binding.lat.text.toString()
            val lon = binding.longuitud.text.toString()

            binding.ubi2Lat.text = lat
            binding.ubi2Long.text = lon
        }

        binding.calcDistance.setOnClickListener {
            val distance = calculatehaversineDistance()
            binding.resultado.text = "Resultado: ${String.format("%.2f", distance)} km"
        }

    }


    @SuppressLint("MissingPermission")
    private fun startLocation() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    binding.lat.text = location.latitude.toString()
                    binding.longuitud.text = location.longitude.toString()
                }
            }
        )
    }

    private fun calculatehaversineDistance(
    ): Double {

        val lat1 = binding.ubi1Lat.text.toString().toDouble()
        val long1 = binding.ubi1Long.text.toString().toDouble()
        val lat2 = binding.ubi2Lat.text.toString().toDouble()
        val long2 = binding.ubi2Long.text.toString().toDouble()

        val radius = 6371

        val lat1Rad = Math.toRadians(lat1)
        val long1Rad = Math.toRadians(long1)
        val lat2Rad = Math.toRadians(lat2)
        val long2Rad = Math.toRadians(long2)

        val dLat = lat2Rad - lat1Rad
        val dLong = long2Rad - long1Rad

        val a = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLong/2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1-a))

        return radius * c
    }


    override fun onResume() {
        super.onResume()
        startLocation()
    }
}