package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginAdminActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.editEmail)
        val passwordEditText = findViewById<EditText>(R.id.editPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    startActivity(Intent(this, AdminDashboardActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Échec de connexion", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
