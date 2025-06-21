package com.jesusrevient.cantique

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.jesusrevient.cantique.models.Song
import java.io.File

class SongAdapter(
    private var songs: List<Song>,
    private val emptyTextView: TextView
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardContainer: View = itemView.findViewById(R.id.song_card_container)
        val titleText: TextView = itemView.findViewById(R.id.song_title)
        val authorText: TextView = itemView.findViewById(R.id.song_author)
        val pdfLinkText: TextView = itemView.findViewById(R.id.song_pdf_link)
        val audioLinkText: TextView = itemView.findViewById(R.id.song_audio_link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        val context = holder.itemView.context

        holder.titleText.text = "${song.numero}. ${song.titre}"
        holder.authorText.text = "Auteur : ${song.auteur}"
        holder.pdfLinkText.text = "Voir partition PDF"
        holder.audioLinkText.text = "Écouter Audio"

        // Ouvrir fiche détail
        holder.titleText.setOnClickListener {
            val intent = Intent(context, SongDetailActivity::class.java).apply {
                putExtra("titre", song.titre)
                putExtra("auteur", song.auteur)
                putExtra("paroles", song.paroles)
                putExtra("categorie", song.categorie)
                putExtra("audioUrl", song.audioUrl)
                putExtra("partitionPdfUrl", song.partitionPdfUrl)
            }
            context.startActivity(intent)
        }

        if (!song.partitionPdfUrl.isNullOrEmpty()) {
            holder.pdfLinkText.visibility = View.VISIBLE
            holder.pdfLinkText.setOnClickListener {
                val intent = Intent(context, PdfViewerActivity::class.java).apply {
                    putExtra("pdfUrl", song.partitionPdfUrl)
                }
                context.startActivity(intent)
            }
        } else {
            holder.pdfLinkText.visibility = View.GONE
        }

        if (!song.audioUrl.isNullOrEmpty()) {
            holder.audioLinkText.visibility = View.VISIBLE
            holder.audioLinkText.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(Uri.parse(song.audioUrl), "audio/*")
                }
                context.startActivity(intent)
            }
        } else {
            holder.audioLinkText.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = songs.size

    fun updateList(newList: List<Song>) {
        songs = newList
        notifyDataSetChanged()
        emptyTextView.visibility = if (songs.isEmpty()) View.VISIBLE else View.GONE
    }

    // Cette fonction reste disponible si nécessaire ailleurs
    private fun isSongDownloadedLocally(context: Context, numero: String): Boolean {
        val dir = context.getExternalFilesDir(null)
        val textFile = File(dir, "$numero.txt")
        val audioFile = File(dir, "$numero.mp3")
        val pdfFile = File(dir, "$numero.pdf")
        return textFile.exists() || audioFile.exists() || pdfFile.exists()
    }
}
