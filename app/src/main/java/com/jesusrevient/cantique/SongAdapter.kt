package com.jesusrevient.cantique

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class SongDetailActivity : AppCompatActivity() {

    private lateinit var titre: String
    private var auteur: String? = null
    private var paroles: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        titre = intent.getStringExtra("titre") ?: "Titre inconnu"
        auteur = intent.getStringExtra("auteur") ?: "Auteur inconnu"
        paroles = intent.getStringExtra("paroles") ?: "Paroles non disponibles"
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
        val downloadIcon = findViewById<ImageView>(R.id.download_icon)
        val rootLayout = findViewById<RelativeLayout>(R.id.detail_root)

        titleTextView.text = "🔥 $titre 🔥"
        authorTextView.text = auteur
        categoryTextView.text = categorie ?: "Catégorie inconnue"
        lyricsTextView.text = paroles

        // Clic prolongé sur toute la page
        registerForContextMenu(rootLayout)
        rootLayout.setOnLongClickListener {
            openContextMenu(rootLayout)
            true
        }

        if (!audioUrl.isNullOrEmpty()) {
            playButton.visibility = View.VISIBLE
            audioAvailableIcon.visibility = View.VISIBLE
            audioUnavailableIcon.visibility = View.GONE
            playButton.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(audioUrl)))
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
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(partitionPdfUrl)))
            }
        } else {
            sheetButton.visibility = View.GONE
            pdfAvailableIcon.visibility = View.GONE
            pdfUnavailableLayout.visibility = View.VISIBLE
        }

        unavailableIconsLayout.visibility = if (audioUrl.isNullOrEmpty() && partitionPdfUrl.isNullOrEmpty()) View.VISIBLE else View.GONE

        val numero = titre.substringBefore('.').trim()
        if (isSongDownloadedLocally(numero)) {
            downloadIcon.visibility = View.VISIBLE
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.setHeaderTitle("Options")
        menu?.add(0, 1, 0, "Partager ce cantique")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            1 -> {
                val texte = "Titre: $titre\nAuteur: $auteur\n\n$paroles"
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, texte)
                }
                startActivity(Intent.createChooser(intent, "Partager ce cantique via"))
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun isSongDownloadedLocally(numero: String): Boolean {
        val dir = getExternalFilesDir(null)
        val textFile = File(dir, "$numero.txt")
        val audioFile = File(dir, "$numero.mp3")
        val pdfFile = File(dir, "$numero.pdf")
        return textFile.exists() || audioFile.exists() || pdfFile.exists()
    }
}
