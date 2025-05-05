package com.jesusrevient.cantique

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SongDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Étape 1 : Test d'inflation du layout avec logs
        try {
            Log.d("SongDetailActivity", "Avant setContentView")
            setContentView(R.layout.activity_song_detail)
            Log.d("SongDetailActivity", "Après setContentView")
        } catch (e: Exception) {
            Log.e("SongDetailActivity", "Erreur lors de setContentView", e)
            finish()
            return
        }

        // Étape 2 : Lecture des extras avec log
        val titre = intent.getStringExtra("titre")
        val auteur = intent.getStringExtra("auteur")
        val paroles = intent.getStringExtra("paroles")
        val categorie = intent.getStringExtra("categorie")
        val audioUrl = intent.getStringExtra("audioUrl")
        val sheetUrl = intent.getStringExtra("sheetUrl")

        Log.d("SongDetailActivity", "Extras reçus : titre=$titre, auteur=$auteur, catégorie=$categorie")

        // Étape 3 : Liaison des vues
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val authorTextView = findViewById<TextView>(R.id.authorTextView)
        val categoryTextView = findViewById<TextView>(R.id.categoryTextView)
        val lyricsTextView = findViewById<TextView>(R.id.lyricsTextView)
        val playButton = findViewById<Button>(R.id.playButton)
        val sheetButton = findViewById<Button>(R.id.sheetButton)

        // Étape 4 : Remplissage des vues
        titleTextView.text = titre ?: "Titre inconnu"
        authorTextView.text = "Auteur : ${auteur ?: "inconnu"}"
        categoryTextView.text = "Catégorie : ${categorie ?: "inconnue"}"
        lyricsTextView.text = paroles ?: "Paroles non disponibles"

        // Étape 5 : Actions des boutons
        playButton.setOnClickListener {
            audioUrl?.let {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            }
        }

        sheetButton.setOnClickListener {
            sheetUrl?.let {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            }
        }
    }
}
