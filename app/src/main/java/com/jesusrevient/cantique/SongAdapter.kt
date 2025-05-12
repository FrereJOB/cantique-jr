package com.jesusrevient.cantique

import android.net.Uri
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesusrevient.cantique.models.Song

class SongAdapter(
    private var songs: List<Song>,
    private val emptyTextView: TextView
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.song_title)
        val authorText: TextView = itemView.findViewById(R.id.song_author)
        val pdfLinkText: TextView = itemView.findViewById(R.id.song_pdf_link)
        val audioLinkText: TextView = itemView.findViewById(R.id.song_audio_link)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.titleText.text = "${song.numero}. ${song.titre}"
        holder.authorText.text = "Auteur : ${song.auteur}"
        holder.pdfLinkText.text = "Voir partition PDF"
        holder.audioLinkText.text = "Écouter Audio"

        val context = holder.itemView.context

        // Titre cliquable → écran de détails
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

        // Ouvre la partition PDF dans PdfViewerActivity
        holder.pdfLinkText.setOnClickListener {
            val intent = Intent(context, PdfViewerActivity::class.java).apply {
                putExtra("pdfUrl", song.partitionPdfUrl)
            }
            context.startActivity(intent)
        }

        // Lien vers le fichier audio
        holder.audioLinkText.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(Uri.parse(song.audioUrl), "audio/*")
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = songs.size

    // 🔄 Mise à jour dynamique de la liste
    fun updateList(newList: List<Song>) {
        songs = newList
        notifyDataSetChanged()

        // Affiche ou masque le message "Aucun cantique trouvé"
        emptyTextView.visibility = if (songs.isEmpty()) View.VISIBLE else View.GONE
    }
}
