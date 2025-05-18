package com.example.bitestore.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.bitestore.R
import com.example.bitestore.fragments.CarritoFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductosActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)


        val botonmap = findViewById<FloatingActionButton>(R.id.btn_mapa)
        botonmap.setOnClickListener {
            val intent = Intent(this, MapaActivity::class.java)
            startActivity(intent)
        }

        val iconoCarrito = findViewById<ImageView>(R.id.icono_carrito)
        iconoCarrito.setOnClickListener {
            val carritoFragment = CarritoFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CarritoFragment())
                .addToBackStack(null)
                .commit()
        }

    }


}