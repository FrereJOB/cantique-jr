package com.jesusrevient.cantique

import androidx.appcompat.app.AppCompatActivity
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

        db.collection("songs").get()
            .addOnSuccessListener { documents ->
                val songs = documents.map { it["title"].toString() }
                songsList.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songs)
            }
    }
}
