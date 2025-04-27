package com.example.bitestore.Activities
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bitestore.R

class InicioSesionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciarsesion)


        val buttonYaTengoCuenta = findViewById<Button>(R.id.buttonCrearCuenta)
        buttonYaTengoCuenta.setOnClickListener {
            val intent = Intent(this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }


        val iniciarsesion = findViewById<Button>(R.id.buttonInicioSesion)
        iniciarsesion.setOnClickListener {
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)
        }
    }
}