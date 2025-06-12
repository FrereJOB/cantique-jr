package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.*
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

    private val handler = Handler(Looper.getMainLooper())
    private var currentImageIndex = 0
    private lateinit var imageViewLouange: ImageView
    private val images = listOf(
        R.drawable.louange,
        R.drawable.louange1,
        R.drawable.louange2,
        R.drawable.louange3
    )

    private val slideshowRunnable = object : Runnable {
        override fun run() {
            val fadeOut = AlphaAnimation(1f, 0f).apply {
                duration = 1000
                fillAfter = true
            }

            val fadeIn = AlphaAnimation(0f, 1f).apply {
                duration = 1000
                fillAfter = true
            }

            imageViewLouange.startAnimation(fadeOut)
            fadeOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                override fun onAnimationStart(animation: android.view.animation.Animation) {}
                override fun onAnimationRepeat(animation: android.view.animation.Animation) {}
                override fun onAnimationEnd(animation: android.view.animation.Animation) {
                    imageViewLouange.setImageResource(images[currentImageIndex])
                    imageViewLouange.startAnimation(fadeIn)
                    currentImageIndex = (currentImageIndex + 1) % images.size
                }
            })

            handler.postDelayed(this, 10000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewLouange = findViewById(R.id.imageViewLouange)
        startImageSlideshow()

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val toggle = object : ActionBarDrawerToggle(
            this, drawerLayout, R.string.open_drawer, R.string.close_drawer
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                startImageSlideshow()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                stopImageSlideshow()
            }
        }

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

    private fun startImageSlideshow() {
        handler.removeCallbacks(slideshowRunnable)
        handler.post(slideshowRunnable)
    }

    private fun stopImageSlideshow() {
        handler.removeCallbacks(slideshowRunnable)
    }

    private fun fetchSongs() {
        db.collection("cantiques")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val song = document.toObject(Song::class.java)
                    songList.add(song)
                    fullSongList.add(song)
                }
                adapter.updateList(songList)
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
            R.id.nav_group -> startActivity(Intent(this, AProposGroupeActivity::class.java))
            R.id.nav_app -> startActivity(Intent(this, AProposAppActivity::class.java))
            R.id.nav_admin -> startActivity(Intent(this, LoginAdminActivity::class.java))
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

    override fun onPause() {
        super.onPause()
        stopImageSlideshow()
    }

    override fun onResume() {
        super.onResume()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            startImageSlideshow()
        }
    }
}
