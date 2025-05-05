package com.jesusrevient.cantique


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.jesusrevient.cantique.models.Song

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore
        val songsList = findViewById<ListView>(R.id.songs_list)

        db.collection("cantique").get()
            .addOnSuccessListener { documents ->
                val songs = documents.map { it.toObject(Song::class.java) }

                val titles = songs.map { it.titre }
                songsList.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titles)

                songsList.setOnItemClickListener { _, _, position, _ ->
                    val selectedSong = songs[position]
                    val intent = Intent(this, SongDetailActivity::class.java).apply {
                        putExtra("titre", selectedSong.titre)
                        putExtra("auteur", selectedSong.auteur)
                        putExtra("paroles", selectedSong.paroles)
                        putExtra("categorie", selectedSong.categorie)
                    }
                    startActivity(intent)
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
