package com.jesusrevient.cantique

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
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

        val audioIcon = findViewById<ImageView>(R.id.audioUnavailableIcon)
        val pdfLayout = findViewById<LinearLayout>(R.id.pdfUnavailableLayout)

        // 🔥 Titre avec flammes
        titleTextView.text = if (!titre.isNullOrBlank()) "🔥 $titre 🔥" else "🔥 Titre inconnu 🔥"

        // Infos principales
        categoryTextView.text = categorie ?: "Catégorie inconnue"
        authorTextView.text = auteur ?: "Auteur inconnu"
        lyricsTextView.text = paroles ?: "Paroles non disponibles"

        // Affichage ou masquage du bouton audio
        if (!audioUrl.isNullOrEmpty()) {
            playButton.visibility = View.VISIBLE
            audioIcon.visibility = View.GONE
            playButton.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(audioUrl)))
                } catch (e: Exception) {
                    Toast.makeText(this, "Impossible d’ouvrir le chant audio.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            playButton.visibility = View.GONE
            audioIcon.visibility = View.VISIBLE
        }

        // Affichage ou masquage du bouton PDF
        if (!partitionPdfUrl.isNullOrEmpty()) {
            sheetButton.visibility = View.VISIBLE
            pdfLayout.visibility = View.GONE
            sheetButton.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(partitionPdfUrl)))
                } catch (e: Exception) {
                    Toast.makeText(this, "Impossible d’ouvrir la partition.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            sheetButton.visibility = View.GONE
            pdfLayout.visibility = View.VISIBLE
        }
    }
}
