package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RecueilSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recueil_selection)

        // ImageViews correspondant aux IDs définis dans le layout XML
        val imageCantiqueJR = findViewById<ImageView>(R.id.imageCantiqueJR)
        val imageVoiesEternel = findViewById<ImageView>(R.id.imageVoiesEternel)
        val imageChantsVictoire = findViewById<ImageView>(R.id.imageChantsVictoire)

        imageCantiqueJR.setOnClickListener {
            ouvrirRecueil("cantiques", "Recueil Cantique JR sélectionné")
        }

        imageVoiesEternel.setOnClickListener {
            ouvrirRecueil("voies_eternel", "Recueil Les Voies de l'Éternel sélectionné")
        }

        imageChantsVictoire.setOnClickListener {
            ouvrirRecueil("chants_victoire", "Recueil Les Chants de Victoire sélectionné")
        }
    }

    private fun ouvrirRecueil(nomCollection: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("collection", nomCollection)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
}
