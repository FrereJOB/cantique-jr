package com.jesusrevient.cantique


import com.jesusrevient.cantique.models.Song

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

// Adaptateur pour afficher les cantiques dans le ListView
class SongAdapter(context: Context, songs: List<Song>)
    : ArrayAdapter<Song>(context, 0, songs) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Réutilise la vue ou l'inflate si nécessaire
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        
        // Récupère les TextView prédéfinis
        val titleView = view.findViewById<TextView>(android.R.id.text1)
        val authorView = view.findViewById<TextView>(android.R.id.text2)
        
        // Récupère l'objet Song à la position courante
        val song = getItem(position)
        
        // Remplit les TextViews avec le titre et l'auteur du chant
        titleView.text = song?.titre
        authorView.text = song?.auteur
        
        return view
    }
}
