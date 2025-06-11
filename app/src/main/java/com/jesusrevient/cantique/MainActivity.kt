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
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.jesusrevient.cantique.models.Song

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        val navView: NavigationView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener(this)

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

        // 🔍 Logique de recherche
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

//mise à jour automatique de cantiques
private fun fetchSongs() {
    db.collection("cantiques")
        .addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("MainActivity", "Erreur d'écoute", exception)
                return@addSnapshotListener
            }

            snapshot?.let {
                songList.clear()
                fullSongList.clear()
                for (document in it.documents) {
                    val song = document.toObject(Song::class.java)
                    song?.let {
                        songList.add(it)
                        fullSongList.add(it)
                    }
                }
                adapter.updateList(songList)
            }
        }
}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_group -> {
                Toast.makeText(this, "À propos du groupe", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AProposGroupeActivity::class.java))
            }
            R.id.nav_app -> {
                Toast.makeText(this, "À propos de l'application", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AProposAppActivity::class.java))
            }
            R.id.nav_admin -> {
                startActivity(Intent(this, LoginAdminActivity::class.java))
            }
            R.id.nav_share -> {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Téléchargez l'application Cantique JR sur votre téléphone !")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Partager via"))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
