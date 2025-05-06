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

        titleTextView.text = titre ?: "Titre inconnu"
        authorTextView.text = "Auteur : ${auteur ?: "inconnu"}"
        categoryTextView.text = "Catégorie : ${categorie ?: "inconnue"}"
        lyricsTextView.text = paroles ?: "Paroles non disponibles"

        playButton.setOnClickListener {
            audioUrl?.let { url ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }

        sheetButton.setOnClickListener {
            partitionPdfUrl?.let { url ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
    }
}
