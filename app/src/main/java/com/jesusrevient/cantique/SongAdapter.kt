package com.jesusrevient.cantique

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesusrevient.cantique.models.Song

class SongAdapter(private val songs: List<Song>) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

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

        // Lien vers la partition PDF
        holder.pdfLinkText.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(song.partitionPdfUrl))
            it.context.startActivity(intent)
        }

        // Lien vers le fichier audio
        holder.audioLinkText.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(song.audioUrl))
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = songs.size
}
