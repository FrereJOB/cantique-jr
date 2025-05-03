package com.jesusrevient.cantique

import android.os.Bundle
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

        findViewById<TextView>(R.id.song_title).text = titre
        findViewById<TextView>(R.id.song_author).text = "Auteur : $auteur"
        findViewById<TextView>(R.id.song_category).text = "Catégorie : $categorie"
        findViewById<TextView>(R.id.song_lyrics).text = paroles
    }
}
