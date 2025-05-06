package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdminDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val btnAjouter = findViewById<Button>(R.id.btnAjouterCantique)
        val btnModifier = findViewById<Button>(R.id.btnModifierCantique)
        val btnSupprimer = findViewById<Button>(R.id.btnSupprimerCantique)

        btnAjouter.setOnClickListener {
            startActivity(Intent(this, AjouterCantiqueActivity::class.java))
        }

        btnModifier.setOnClickListener {
            startActivity(Intent(this, ModifierCantiqueActivity::class.java))
        }

        btnSupprimer.setOnClickListener {
            startActivity(Intent(this, SupprimerCantiqueActivity::class.java))
        }
    }
}
