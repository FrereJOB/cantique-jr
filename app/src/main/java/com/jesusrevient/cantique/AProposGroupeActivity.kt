package com.jesusrevient.cantique

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jesusrevient.cantique.databinding.ActivityAproposGroupeBinding

class AProposGroupeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAproposGroupeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAproposGroupeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "À propos du groupe"

        val htmlContent = """
            <html><body>
            <h2 style="color:#4E342E;">JESUS-REVIENT TV</h2>
            <b>Description</b><br/>
            Mouvement international d'évangélisation prêchant la repentance, la sanctification et la doctrine biblique pour préparer les saints à l'Enlèvement.<br/><br/>
            <b>Siège :</b><br/>
            Bénin, Dekoungbé, Hédomey (200m après la Pharmacie Hédomey).<br/>
            <b>Tél. :</b> (+229) 97 25 35 39 / 94 33 69 05<br/>
            <b>Chaîne TV :</b> JESUS-REVIENT.TV (Satellite Amos 17, décodeur Strong)<br/><br/>
            </body></html>
        """.trimIndent()

        binding.textInfoGroupe.text = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
        binding.textInfoGroupe.movementMethod = LinkMovementMethod.getInstance()

        setupSocialLinks()
    }

    private fun setupSocialLinks() {
        val links = listOf(
            Triple(R.id.link_site, R.drawable.site, "https://jesusrevient.tv"),
            Triple(R.id.link_facebook_page, R.drawable.facebook, "https://facebook.com/groupejesusrevient"),
            Triple(R.id.link_facebook_group, R.drawable.facebook, "https://facebook.com/groups/jesusrevient.tv"),
            Triple(R.id.link_whatsapp, R.drawable.whatsapp, "https://www.whatsapp.com/channel/0029VaDKtleHgZWfIv4SNW3z"),
            Triple(R.id.link_telegram_channel, R.drawable.telegram, "https://t.me/jesusrevient"),
            Triple(R.id.link_telegram_group, R.drawable.telegram, "https://t.me/jesusrevient_tv"),
            Triple(R.id.link_twitter, R.drawable.twitter, "https://twitter.com/jesusrevienttv"),
            Triple(R.id.link_gps, R.drawable.gps, "https://maps.app.goo.gl/SFS46YG9JsMCuCW27"),
            Triple(R.id.link_android, R.drawable.site, "https://play.google.com/store/apps/details?id=com.maougnonjesusrevient.jesus_revient_tv"),
        )

        val labels = listOf(
            "Site web", "Page Facebook", "Groupe Facebook", "Chaîne WhatsApp", "Canal Telegram",
            "Groupe Telegram", "Twitter", "Localisation GPS", "Application Android"
        )

        links.forEachIndexed { index, (layoutId, iconRes, url) ->
            val layout = findViewById<LinearLayout>(layoutId)
            val icon: ImageView = view.findViewById(R.id.icon_social)
            val text: TextView = view.findViewById(R.id.social_link_text)


            icon.setImageResource(iconRes)
            text.text = labels[index]
            layout.setOnClickListener { openUrl(url) }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
