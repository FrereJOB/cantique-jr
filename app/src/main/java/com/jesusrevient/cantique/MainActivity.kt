package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
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
    private lateinit var autoComplete: AutoCompleteTextView
    private val db = FirebaseFirestore.getInstance()
    private lateinit var collectionName: String  // nom dynamique du recueil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupérer le nom de la collection à partir de l'Intent
        collectionName = intent.getStringExtra("collection") ?: "cantiques"
        Log.d("MainActivity", "Collection reçue : $collectionName")

        // 🔵 Met à jour dynamiquement le titre du recueil
        val recueilTextView = findViewById<TextView>(R.id.recueilTextView)
        recueilTextView.text = when (collectionName) {
            "voies_eternel" -> "Les Voies de l'Éternel"
            "chants_victoire" -> "Les Chants de Victoire"
            else -> "Cantiques Jésus-Revient"
        }

        // Initialiser les composants
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

        autoComplete = findViewById(R.id.searchEditText)
        val searchButton = findViewById<ImageButton>(R.id.searchButton)

        autoComplete.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                filterSongs(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        searchButton.setOnClickListener {
            val query = autoComplete.text.toString().trim()
            filterSongs(query)
        }

        fetchSongs()
    }

    private fun fetchSongs() {
        Log.d("MainActivity", "Chargement de la collection Firestore : $collectionName")

        db.collection(collectionName)
            .get()
            .addOnSuccessListener { documents ->
                songList.clear()
                fullSongList.clear()
                val titleSuggestions = mutableListOf<String>()

                for (document in documents) {
                    try {
                        val song = document.toObject(Song::class.java)
                        songList.add(song)
                        fullSongList.add(song)
                        titleSuggestions.add(song.titre)
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Erreur de désérialisation : ${e.message}")
                    }
                }

                adapter.updateList(songList)

                val autoCompleteAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    titleSuggestions.distinct()
                )
                autoComplete.setAdapter(autoCompleteAdapter)

                if (songList.isEmpty()) {
                    Log.w("MainActivity", "Aucun chant trouvé ou erreur de désérialisation. Collection: $collectionName")
                    Toast.makeText(this, "Aucun cantique trouvé dans ce recueil.", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Erreur lors du chargement des cantiques", exception)
                Toast.makeText(this, "Échec du chargement : ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun filterSongs(query: String) {
        val lowerCaseQuery = query.lowercase()
        val filteredList = fullSongList.filter { song ->
            song.titre.lowercase().contains(lowerCaseQuery) ||
            song.auteur.lowercase().contains(lowerCaseQuery) ||
            song.paroles?.lowercase()?.contains(lowerCaseQuery) == true ||
            song.numero.toString().contains(lowerCaseQuery) ||
            song.categorie.lowercase().contains(lowerCaseQuery)
        }
        adapter.updateList(filteredList)
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
