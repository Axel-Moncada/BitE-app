package com.example.bitestore.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bitestore.R
import com.google.firebase.auth.FirebaseAuth


class CrearCuentaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crearcuenta)

        // texto ya tengo cuenta
        val textYaTengoCuenta = findViewById<TextView>(R.id.yatengocrearcuenta)

        textYaTengoCuenta.setOnClickListener {
            val intent = Intent(this, InicioSesionActivity::class.java)
            startActivity(intent)
        }

        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val inputemail = findViewById<EditText>(R.id.inputemail)
        val inputPassword = findViewById<EditText>(R.id.TextPassword)

        signUpButton.setOnClickListener {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                inputemail.text.toString(),
                inputPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this@CrearCuentaActivity, ProductosActivity::class.java)
                    startActivity(intent)
                } else {
                    showAlert()
                }
            }
        }


    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}