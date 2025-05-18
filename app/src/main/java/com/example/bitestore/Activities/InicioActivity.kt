package com.example.bitestore.Activities
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bitestore.R
import android.widget.Button


class InicioActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Botón de "Iniciar Sesión"
        val buttonInicioSesion = findViewById<Button>(R.id.signUpButton)

        buttonInicioSesion.setOnClickListener {
            val intent = Intent(this, InicioSesionActivity::class.java)
            startActivity(intent)
        }

        // Botón de "Crear cuenta"
        val buttonCrearCuenta= findViewById<Button>(R.id.buttonCrearCuenta)

        buttonCrearCuenta.setOnClickListener {
            val intent = Intent(this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }


    }


}
