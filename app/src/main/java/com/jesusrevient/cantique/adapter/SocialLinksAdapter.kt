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
import com.jesusrevient.cantique.models.SocialLink

class SocialLinksAdapter(
    private val context: Context,
    private val socialLinks: List<SocialLink>
) : RecyclerView.Adapter<SocialLinksAdapter.SocialLinkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialLinkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_social_link, parent, false)
        return SocialLinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: SocialLinkViewHolder, position: Int) {
        val socialLink = socialLinks[position]
        holder.bind(socialLink)
    }

    override fun getItemCount(): Int = socialLinks.size

    inner class SocialLinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.icon_social)
        private val label: TextView = itemView.findViewById(R.id.social_link_text)

        fun bind(link: SocialLink) {
            icon.setImageResource(link.iconRes)
            label.text = link.label

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
                context.startActivity(intent)
            }
        }
    }
}
