package com.jesusrevient.cantique.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesusrevient.cantique.R
import com.jesusrevient.cantique.model.SocialLink

class SocialLinksAdapter(
    private val context: Context,
    private val links: List<SocialLink>
) : RecyclerView.Adapter<SocialLinksAdapter.LinkViewHolder>() {

    class LinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.icon_social)
        val text: TextView = view.findViewById(R.id.social_link_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_social_link, parent, false)
        return LinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        val link = links[position]

        holder.text.text = link.url

        // Définir l'icône en fonction du type
        val iconRes = when (link.type.lowercase()) {
            "facebook" -> R.drawable.facebook
            "telegram" -> R.drawable.telegram
            "whatsapp" -> R.drawable.whatsapp
            "site" -> R.drawable.site
            "gps" -> R.drawable.gps
            "twitter" -> R.drawable.twitter
            "app" -> R.drawable.app
            else -> R.drawable.site
        }
        holder.icon.setImageResource(iconRes)

        // Clic pour ouvrir le lien dans un navigateur
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = links.size
}
