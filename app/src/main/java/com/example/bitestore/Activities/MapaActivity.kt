package com.example.bitestore.Activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.bitestore.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {




    private lateinit var mMap: GoogleMap
    private var mapReady = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private val ubicaciones = listOf(
        LatLng(4.6097, -74.0817),  // Bogotá
        LatLng(3.4516, -76.5320),  // Cali
        LatLng(6.2518, -75.5636),  // Medellín
        LatLng(10.9685, -74.7813), // Barranquilla
        LatLng(10.3910, -75.4794), // Cartagena
        LatLng(7.1193, -73.1227),  // Bucaramanga
        LatLng(4.1420, -73.6266),  // Villavicencio
        LatLng(8.7737, -75.8814),  // Montería
        LatLng(1.2136, -77.2811),  // Pasto
        LatLng(5.0689, -75.5174)   // Manizales
    )

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maplocator)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        findViewById<Button>(R.id.cercana)?.setOnClickListener {
            pedirUbicacionEnTiempoReal()
        }
        val volverbtn = findViewById<Button>(R.id.volver)
        volverbtn.setOnClickListener {
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mapReady = true

        ubicaciones.forEach {
            mMap.addMarker(MarkerOptions().position(it).title("Oficina"))
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicaciones[0], 5f))
    }

    private fun pedirUbicacionEnTiempoReal() {
        if (!mapReady) {
            Toast.makeText(this, "El mapa aún no está listo", Toast.LENGTH_SHORT).show()
            return
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001
            )
            return
        }

        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = 1000
            fastestInterval = 500
            numUpdates = 1
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation
                if (location != null) {
                    usarUbicacion(location)
                } else {
                    Toast.makeText(this@MapaActivity, "Ubicación no disponible", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun usarUbicacion(location: Location) {
        val userLatLng = LatLng(location.latitude, location.longitude)
        val closest = ubicaciones.minByOrNull { destino ->
            distanciaEntre(userLatLng, destino)
        }

        if (closest != null) {
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(closest).title("Oficina más cercana"))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(closest, 14f))
            Toast.makeText(this, "Oficina más cercana ubicada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun distanciaEntre(start: LatLng, end: LatLng): Float {
        val resultados = FloatArray(1)
        Location.distanceBetween(
            start.latitude, start.longitude,
            end.latitude, end.longitude,
            resultados
        )
        return resultados[0]
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::fusedLocationClient.isInitialized && ::locationCallback.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }


}
