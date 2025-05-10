package com.jesusrevient.cantique.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesusrevient.cantique.R
import com.jesusrevient.cantique.models.SocialLink

class SocialLinksAdapter(private val links: List<SocialLink>) :
    RecyclerView.Adapter<SocialLinksAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconImageView: ImageView = view.findViewById(R.id.iconImageView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_social_link, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val link = links[position]
        holder.nameTextView.text = link.name
        holder.iconImageView.setImageResource(link.iconResId) // ✅ ici
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = links.size
}
