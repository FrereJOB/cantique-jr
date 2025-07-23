package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginAdminActivity : AppCompatActivity() {

    // Identifiants admin en dur (à améliorer avec Firebase Auth plus tard)
    private val adminEmail = "admin@example.com"
    private val adminPassword = "motdepasse123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        // Fonction du bouton retour dans la bannière
        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            finish() // Ferme l'activité actuelle pour revenir à la précédente
        }

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email == adminEmail && password == adminPassword) {
                startActivity(Intent(this, AdminDashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Identifiants incorrects", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
