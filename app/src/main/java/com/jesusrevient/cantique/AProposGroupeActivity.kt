package com.jesusrevient.cantique

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AproposGroupeActivity : AppCompatActivity() {

    data class SocialLink(
        val iconResId: Int,
        val label: String,
        val url: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apropos_groupe)

        val textInfo = findViewById<TextView>(R.id.text_info_groupe)
        textInfo.text = getString(R.string.apropos_groupe_texte)

        val container = findViewById<LinearLayout>(android.R.id.content)
            .findViewById<LinearLayout>(R.id.content) // ou directement LinearLayout après ScrollView

        val socialLinks = listOf(
            SocialLink(R.drawable.site, "Site Web", "https://example.com"),
            SocialLink(R.drawable.facebook, "Page Facebook", "https://facebook.com/"),
            SocialLink(R.drawable.whatsapp, "Groupe WhatsApp", "https://chat.whatsapp.com/..."),
            SocialLink(R.drawable.telegram, "Groupe Telegram", "https://t.me/..."),
            SocialLink(R.drawable.instagram, "Instagram", "https://instagram.com/..."),
            SocialLink(R.drawable.youtube, "Chaîne YouTube", "https://youtube.com/..."),
            SocialLink(R.drawable.tiktok, "TikTok", "https://tiktok.com/..."),
            SocialLink(R.drawable.email, "Email", "mailto:contact@example.com")
        )

        // Les includes commencent à l’index 1 (le premier est text_info_groupe)
        for (i in socialLinks.indices) {
            val itemView = container.getChildAt(i + 1) // +1 pour sauter le TextView
            if (itemView is View) {
                val icon = itemView.findViewById<ImageView>(R.id.link_icon)
                val text = itemView.findViewById<TextView>(R.id.link_text)

                val link = socialLinks[i]
                icon.setImageResource(link.iconResId)
                text.text = link.label

                itemView.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
                    startActivity(intent)
                }
            }
        }
    }
}
