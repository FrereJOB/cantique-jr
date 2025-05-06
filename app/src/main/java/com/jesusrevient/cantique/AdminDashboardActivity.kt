package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesusrevient.cantique.databinding.ActivityAdminDashboardBinding

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Tableau de bord administrateur"

        binding.btnAjouter.setOnClickListener {
            // Lancer une activité pour ajouter un cantique
            startActivity(Intent(this, AjouterCantiqueActivity::class.java))
        }

        binding.btnModifier.setOnClickListener {
            // Afficher la liste pour choisir un chant à modifier
        }

        binding.btnSupprimer.setOnClickListener {
            // Afficher la liste pour choisir un chant à supprimer
        }
    }
}
