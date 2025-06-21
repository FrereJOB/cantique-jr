package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.TextView
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
    private lateinit var searchAutoComplete: AutoCompleteTextView
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

        searchAutoComplete = findViewById(R.id.searchAutoComplete)
        setupSearchView()

        fetchSongs()
    }

    private fun setupSearchView() {
        // Adapter for suggestions will be set when songs are loaded
        searchAutoComplete.setOnItemClickListener { parent, _, position, _ ->
            val selectedTitle = parent.getItemAtPosition(position) as String
            searchAutoComplete.setText(selectedTitle)
            filterSongs(selectedTitle)
        }
        // Listen text changes for live filtering
        searchAutoComplete.addTextChangedListener(SimpleTextWatcher { text ->
            filterSongs(text)
        })
        // Optionally handle search button press
        findViewById<ImageButton>(R.id.searchButton).setOnClickListener {
            filterSongs(searchAutoComplete.text.toString())
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
                setupAutoCompleteSuggestions(fullSongList)
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Erreur lors du chargement des cantiques", exception)
            }
    }

    private fun setupAutoCompleteSuggestions(songs: List<Song>) {
        val titles = songs.map { it.titre }
        val arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_dropdown_item_1line,
            titles)
        searchAutoComplete.setAdapter(arrayAdapter)
    }

    private fun filterSongs(query: String) {
        val trimmed = query.trim()
        val filtered = if (trimmed.isEmpty()) {
            fullSongList
        } else {
            fullSongList.filter { song ->
                song.titre.contains(trimmed, ignoreCase = true)
                        || song.paroles.contains(trimmed, ignoreCase = true)
            }
        }
        adapter.updateList(filtered)
    }

    // Navigation drawer clicks
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
