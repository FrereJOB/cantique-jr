package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginAdminActivity : AppCompatActivity() {

    private val adminPassword = "motdepasseadmin" // À personnaliser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val enteredPassword = passwordInput.text.toString()
            if (enteredPassword == adminPassword) {
                startActivity(Intent(this, AdminDashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
