package com.jesusrevient.cantique

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

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val authorTextView = findViewById<TextView>(R.id.authorTextView)
        val categoryTextView = findViewById<TextView>(R.id.categoryTextView)
        val lyricsTextView = findViewById<TextView>(R.id.lyricsTextView)
        val playButton = findViewById<Button>(R.id.playButton)
        val sheetButton = findViewById<Button>(R.id.sheetButton)

        // Récupération des données passées via l'intent
        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val category = intent.getStringExtra("category")
        val lyrics = intent.getStringExtra("lyrics")
        val audioUrl = intent.getStringExtra("audioUrl")
        val sheetUrl = intent.getStringExtra("sheetUrl")

        // Affichage dans la vue
        titleTextView.text = title
        authorTextView.text = "Auteur : $author"
        categoryTextView.text = "Catégorie : $category"
        lyricsTextView.text = lyrics

        // Bouton pour jouer l’audio
        playButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(audioUrl), "audio/*")
            startActivity(intent)
        }

        // Bouton pour voir la partition
        sheetButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sheetUrl))
            startActivity(intent)
        }
    }
}
