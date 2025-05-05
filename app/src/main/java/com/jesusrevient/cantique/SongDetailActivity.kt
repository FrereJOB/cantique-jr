package com.jesusrevient.cantique


import com.jesusrevient.cantique.models.Song

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SongDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        // Récupération des extras
        val titre = intent.getStringExtra("titre")
        val auteur = intent.getStringExtra("auteur")
        val paroles = intent.getStringExtra("paroles")
        val categorie = intent.getStringExtra("categorie")
        val audioUrl = intent.getStringExtra("audioUrl")   // futur usage
        val sheetUrl = intent.getStringExtra("sheetUrl")   // futur usage

        // Liaison avec les vues du layout
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val authorTextView = findViewById<TextView>(R.id.authorTextView)
        val categoryTextView = findViewById<TextView>(R.id.categoryTextView)
        val lyricsTextView = findViewById<TextView>(R.id.lyricsTextView)

        val playButton = findViewById<Button>(R.id.playButton)
        val sheetButton = findViewById<Button>(R.id.sheetButton)

        // Mise à jour des vues avec les données
        titleTextView.text = titre ?: "Titre inconnu"
        authorTextView.text = "Auteur : ${auteur ?: "inconnu"}"
        categoryTextView.text = "Catégorie : ${categorie ?: "inconnue"}"
        lyricsTextView.text = paroles ?: "Paroles non disponibles"

        // Action des boutons (à activer quand les URLs sont disponibles)
        playButton.setOnClickListener {
            audioUrl?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            }
        }

        sheetButton.setOnClickListener {
            sheetUrl?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(intent)
            }
        }
    }
}
