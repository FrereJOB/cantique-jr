package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class RecueilSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recueil_selection)

        val imageCantiqueJR = findViewById<ImageView>(R.id.imageCantiqueJR)
        val imageVoiesEternel = findViewById<ImageView>(R.id.imageVoiesEternel)
        val imageChantsVictoire = findViewById<ImageView>(R.id.imageChantsVictoire)

        val intentVersMain = Intent(this, MainActivity::class.java)

        imageCantiqueJR.setOnClickListener {
            startActivity(intentVersMain)
        }

        imageVoiesEternel.setOnClickListener {
            startActivity(intentVersMain)
        }

        imageChantsVictoire.setOnClickListener {
            startActivity(intentVersMain)
        }
    }
}
