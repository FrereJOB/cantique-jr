package com.jesusrevient.cantique

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SongDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        val titre = intent.getStringExtra("titre")
        val auteur = intent.getStringExtra("auteur")
        val paroles = intent.getStringExtra("paroles")
        val categorie = intent.getStringExtra("categorie")
        val audioUrl = intent.getStringExtra("audioUrl")
        val partitionPdfUrl = intent.getStringExtra("partitionPdfUrl")

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val authorTextView = findViewById<TextView>(R.id.authorTextView)
        val categoryTextView = findViewById<TextView>(R.id.categoryTextView)
        val lyricsTextView = findViewById<TextView>(R.id.lyricsTextView)
        val playButton = findViewById<Button>(R.id.playButton)
        val sheetButton = findViewById<Button>(R.id.sheetButton)

        // 🔥 Titre avec flammes
        titleTextView.text = if (!titre.isNullOrBlank()) "🔥 $titre 🔥" else "🔥 Titre inconnu 🔥"

        // Texte normal pour catégorie et auteur
        categoryTextView.text = categorie ?: "Catégorie inconnue"
        authorTextView.text = auteur ?: "Auteur inconnu"
        lyricsTextView.text = paroles ?: "Paroles non disponibles"

        // Bouton audio visible uniquement si URL non vide
        if (!audioUrl.isNullOrEmpty()) {
            playButton.visibility = View.VISIBLE
            playButton.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(audioUrl)))
                } catch (e: Exception) {
                    Toast.makeText(this, "Impossible d’ouvrir le chant audio.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            playButton.visibility = View.GONE
        }

        // Bouton PDF visible uniquement si URL non vide
        if (!partitionPdfUrl.isNullOrEmpty()) {
            sheetButton.visibility = View.VISIBLE
            sheetButton.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(partitionPdfUrl)))
                } catch (e: Exception) {
                    Toast.makeText(this, "Impossible d’ouvrir la partition.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            sheetButton.visibility = View.GONE
        }
    }
}
