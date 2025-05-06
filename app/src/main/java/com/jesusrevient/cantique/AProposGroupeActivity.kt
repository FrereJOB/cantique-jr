package com.jesusrevient.cantique

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesusrevient.cantique.databinding.ActivityAproposGroupeBinding

class AProposGroupeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAproposGroupeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAproposGroupeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "À propos du groupe"

        binding.textInfoGroupe.text = """
            Le groupe Jésus Revient est un ministère chrétien basé à Yaoundé, Cameroun. 
            Il œuvre pour l’édification des croyants à travers la louange, l’adoration, 
            la prédication et l’enseignement de la parole de Dieu.
            
            Nos cantiques sont inspirés par le Saint-Esprit pour fortifier la foi des enfants de Dieu.
            Que Dieu vous bénisse abondamment !
        """.trimIndent()
    }
}
