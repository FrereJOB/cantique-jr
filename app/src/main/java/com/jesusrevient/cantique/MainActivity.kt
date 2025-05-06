package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
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
        adapter = SongAdapter(songList)
        recyclerView.adapter = adapter

        fetchSongs()
    }

    private fun fetchSongs() {
        db.collection("cantique")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val song = document.toObject(Song::class.java)
                    songList.add(song)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Erreur lors du chargement des cantiques", exception)
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
