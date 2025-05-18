package com.example.bitestore.Activities
import android.content.Intent
import android.credentials.GetCredentialRequest
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bitestore.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth








class InicioSesionActivity : AppCompatActivity() {


    private lateinit var btnGoogle: Button
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 123
    private val TAG = "GoogleSignIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciarsesion)


        val buttonYaTengoCuenta = findViewById<Button>(R.id.buttonCrearCuenta)
        buttonYaTengoCuenta.setOnClickListener {
            val intent = Intent(this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        val emailInput = findViewById<EditText>(R.id.emailEditText)
        val passwordInput = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.signUpButton)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, ProductosActivity::class.java)
                            startActivity(intent)
                        } else {
                            showAlert()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestEmail()
            .requestProfile()
            .build()

        // Crear el cliente de google sign in
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        val btnGoogle = findViewById<ImageView>(R.id.googleButton)


        btnGoogle.setOnClickListener {
           signIn()
        }






    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(CompletedTask: Task<GoogleSignInAccount>) {
        try {
            val account = CompletedTask.getResult(ApiException::class.java)
            Log.d(TAG,"signInResult: ${account.email} ")
            Toast.makeText(this, "Bienvenido ${account.email}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)
        }catch (e: ApiException){
            Log.w(TAG, "signInResult:failed code = ${e.statusCode}")
            Toast.makeText(this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)

        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Correo o contraseña incorrectos.")
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }









}