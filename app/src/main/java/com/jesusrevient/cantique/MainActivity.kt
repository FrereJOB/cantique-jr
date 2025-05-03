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

        db.collection("cantique").get()
            .addOnSuccessListener { documents ->
                val songs = documents.mapNotNull { it.toObject(Song::class.java).titre }
                songsList.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, songs)
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}
