package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.jesusrevient.cantique.models.Song

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongAdapter
    private lateinit var songList: MutableList<Song>
    private lateinit var fullSongList: MutableList<Song>
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        val openDrawerButton: ImageButton = findViewById(R.id.open_drawer)
        val closeDrawerButton: ImageButton = findViewById(R.id.close_drawer)

        openDrawerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            closeDrawerButton.visibility = View.VISIBLE
            openDrawerButton.visibility = View.GONE
        }

        closeDrawerButton.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            closeDrawerButton.visibility = View.GONE
            openDrawerButton.visibility = View.VISIBLE
        }

        recyclerView = findViewById(R.id.songs_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        songList = mutableListOf()
        fullSongList = mutableListOf()
        val emptyTextView = findViewById<TextView>(R.id.emptyTextView)
        adapter = SongAdapter(songList, emptyTextView)
        recyclerView.adapter = adapter

        fetchSongs()

        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        val searchButton = findViewById<ImageButton>(R.id.searchButton)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString().trim().lowercase()
            val filteredList = fullSongList.filter { song ->
                song.titre.lowercase().contains(query) ||
                song.auteur.lowercase().contains(query) ||
                song.numero.toString().contains(query) ||
                song.categorie.lowercase().contains(query)
            }
            adapter.updateList(filteredList)
        }
    }

    private fun fetchSongs() {
        db.collection("cantiques")
            .get()
            .addOnSuccessListener { documents ->
                songList.clear()
                fullSongList.clear()
                for (document in documents) {
                    try {
                        val song = document.toObject(Song::class.java)
                        songList.add(song)
                        fullSongList.add(song)
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Erreur de désérialisation : ${e.message}")
                    }
                }
                adapter.updateList(songList)
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Erreur lors du chargement des cantiques", exception)
            }
    }

    fun onGroupClick(view: View) {
        startActivity(Intent(this, AProposGroupeActivity::class.java))
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun onAppClick(view: View) {
        startActivity(Intent(this, AProposAppActivity::class.java))
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun onAdminClick(view: View) {
        startActivity(Intent(this, LoginAdminActivity::class.java))
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    fun onShareClick(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Téléchargez l'application Cantique JR sur votre téléphone !")
        startActivity(Intent.createChooser(intent, "Partager via"))
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}
