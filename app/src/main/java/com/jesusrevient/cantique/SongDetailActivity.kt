package com.jesusrevient.cantique

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import java.io.File

class SongDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        val titre = intent.getStringExtra("titre")
        val auteur = intent.getStringExtra("auteur")
        val paroles = intent.getStringExtra("paroles")
        val categorie = intent.getStringExtra("categorie")
        val audioUrl = intent.getStringExtra("audioUrl")
        val partitionPdfUrl = intent.getStringExtra("partitionPdfUrl")

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val authorTextView = findViewById<TextView>(R.id.authorTextView)
        val categoryTextView = findViewById<TextView>(R.id.categoryTextView)
        val lyricsTextView = findViewById<TextView>(R.id.lyricsTextView)
        val playButton = findViewById<Button>(R.id.playButton)
        val sheetButton = findViewById<Button>(R.id.sheetButton)
        val audioAvailableIcon = findViewById<ImageView>(R.id.audioAvailableIcon)
        val pdfAvailableIcon = findViewById<ImageView>(R.id.pdfAvailableIcon)
        val audioUnavailableIcon = findViewById<ImageView>(R.id.audioUnavailableIcon)
        val pdfUnavailableLayout = findViewById<LinearLayout>(R.id.pdfUnavailableLayout)
        val unavailableIconsLayout = findViewById<LinearLayout>(R.id.unavailableIconsLayout)
        val shareButton = findViewById<ImageButton>(R.id.share_button)
        val downloadIcon = findViewById<ImageView>(R.id.download_icon)

        titleTextView.text = if (!titre.isNullOrBlank()) "🔥 $titre 🔥" else "🔥 Titre inconnu 🔥"
        categoryTextView.text = categorie ?: "Catégorie inconnue"
        authorTextView.text = auteur ?: "Auteur inconnu"
        lyricsTextView.text = paroles ?: "Paroles non disponibles"

        shareButton?.setOnClickListener {
            val texte = "Titre: $titre\nAuteur: $auteur\n\n$paroles"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, texte)
            startActivity(Intent.createChooser(intent, "Partager ce cantique via"))
        }

        // Vérifie si le fichier a été téléchargé
        val numero = titre?.substringBefore('.')?.trim()
        if (!numero.isNullOrEmpty() && isSongDownloadedLocally(numero)) {
            downloadIcon.visibility = View.VISIBLE
            downloadIcon.setImageResource(R.drawable.ic_download) // important
        } else {
            downloadIcon.visibility = View.GONE
        }

        if (!audioUrl.isNullOrEmpty()) {
            playButton.visibility = View.VISIBLE
            audioAvailableIcon.visibility = View.VISIBLE
            audioUnavailableIcon.visibility = View.GONE
            playButton.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(audioUrl)))
                } catch (e: Exception) {
                    Toast.makeText(this, "Impossible d’ouvrir le chant audio.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            playButton.visibility = View.GONE
            audioAvailableIcon.visibility = View.GONE
            audioUnavailableIcon.visibility = View.VISIBLE
        }

        if (!partitionPdfUrl.isNullOrEmpty()) {
            sheetButton.visibility = View.VISIBLE
            pdfAvailableIcon.visibility = View.VISIBLE
            pdfUnavailableLayout.visibility = View.GONE
            sheetButton.setOnClickListener {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(partitionPdfUrl)))
                } catch (e: Exception) {
                    Toast.makeText(this, "Impossible d’ouvrir la partition.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            sheetButton.visibility = View.GONE
            pdfAvailableIcon.visibility = View.GONE
            pdfUnavailableLayout.visibility = View.VISIBLE
        }

        if (audioUrl.isNullOrEmpty() && partitionPdfUrl.isNullOrEmpty()) {
            unavailableIconsLayout.visibility = View.VISIBLE
        } else {
            unavailableIconsLayout.visibility = View.GONE
        }

        val scrollableLayout = findViewById<LinearLayout>(R.id.scrollableLayout)
        scrollableLayout.setOnLongClickListener {
            showContextMenu(it, titre, auteur, paroles)
            true
        }
    }

    private fun isSongDownloadedLocally(numero: String): Boolean {
        val dir = getExternalFilesDir(null)
        val textFile = File(dir, "$numero.txt")
        val audioFile = File(dir, "$numero.mp3")
        val pdfFile = File(dir, "$numero.pdf")
        return textFile.exists() || audioFile.exists() || pdfFile.exists()
    }

    private fun showContextMenu(anchor: View, titre: String?, auteur: String?, paroles: String?) {
        val popup = PopupMenu(this, anchor)
        popup.menuInflater.inflate(R.menu.context_song_menu, popup.menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_partager -> {
                    val texte = "Titre: $titre\nAuteur: $auteur\n\n$paroles"
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, texte)
                    }
                    startActivity(Intent.createChooser(intent, "Partager ce cantique via"))
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}
