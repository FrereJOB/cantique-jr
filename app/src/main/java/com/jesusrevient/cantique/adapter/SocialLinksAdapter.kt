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
import com.jesusrevient.cantique.modeles.SocialLink

class SocialLinksAdapter(
    private val context: Context,
    private val socialLinks: List<SocialLink>
) : RecyclerView.Adapter<SocialLinksAdapter.SocialLinkViewHolder>() {

    class SocialLinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        val linkTextView: TextView = itemView.findViewById(R.id.linkTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialLinkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_social_link, parent, false)
        return SocialLinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: SocialLinkViewHolder, position: Int) {
        val socialLink = socialLinks[position]
        holder.linkTextView.text = socialLink.nom

        val iconResId = when (socialLink.type.lowercase()) {
            "facebook", "groupe_facebook" -> R.drawable.facebook
            "whatsapp" -> R.drawable.whatsapp
            "chaine_telegram", "groupe_telegram" -> R.drawable.telegram
            "gps" -> R.drawable.gps
            "application_android" -> R.drawable.app
            "site_web" -> R.drawable.site
            "twitter" -> R.drawable.twitter
            "youtube" -> R.drawable.youtube
            "email" -> R.drawable.mail
            else -> R.drawable.default_icon
        }

        holder.iconImageView.setImageResource(iconResId)

        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(socialLink.url))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = socialLinks.size
}
