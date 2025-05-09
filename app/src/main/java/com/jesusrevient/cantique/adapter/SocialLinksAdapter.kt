package com.jesusrevient.cantique.adaptateurs

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

class SocialLinksAdapter(private val socialLinks: List<SocialLink>) :
    RecyclerView.Adapter<SocialLinksAdapter.SocialLinkViewHolder>() {

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

    class SocialLinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

        fun bind(socialLink: SocialLink) {
            val context = itemView.context
            nameTextView.text = socialLink.name
            val iconResId = when {
                socialLink.name.contains("Facebook", ignoreCase = true) -> R.drawable.facebook
                socialLink.name.contains("Telegram", ignoreCase = true) -> R.drawable.telegram
                socialLink.name.contains("WhatsApp", ignoreCase = true) -> R.drawable.whatsapp
                socialLink.name.contains("YouTube", ignoreCase = true) -> R.drawable.youtube
                socialLink.name.contains("Android", ignoreCase = true) -> R.drawable.app
                socialLink.name.contains("GPS", ignoreCase = true) -> R.drawable.gps
                socialLink.name.contains("Twitter", ignoreCase = true) -> R.drawable.twitter
                socialLink.name.contains("Email", ignoreCase = true) -> R.drawable.mail
                else -> R.drawable.ic_launcher_foreground
            }
            iconImageView.setImageResource(iconResId)

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(socialLink.url))
                context.startActivity(intent)
            }
        }
    }
}
