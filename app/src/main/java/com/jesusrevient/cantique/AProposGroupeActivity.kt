package com.jesusrevient.cantique

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesusrevient.cantique.adapter.SocialLinksAdapter
import com.jesusrevient.cantique.models.SocialLink

class AProposGroupeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apropos_groupe)

        // Activation du bouton retour
        findViewById<ImageButton>(R.id.back_button)?.setOnClickListener {
            finish()
        }

        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()

        val socialLinks = listOf(
            SocialLink("Groupe Facebook", "https://www.facebook.com/groups/jesusrevient.tv", R.drawable.facebook),
            SocialLink("Page Facebook", "https://www.facebook.com/groupejesusrevient", R.drawable.facebook),
            SocialLink("YouTube", "https://www.youtube.com/@jesusrevienttv", R.drawable.youtube),
            SocialLink("WhatsApp", "https://whatsapp.com/channel/0029VaDKtleHgZWfIv4SNW3z", R.drawable.whatsapp),
            SocialLink("Canal Telegram", "https://t.me/jesusrevient", R.drawable.telegram),
            SocialLink("Twitter", "https://twitter.com/jesusrevienttv", R.drawable.twitter),
            SocialLink("Groupe Telegram", "https://t.me/jesusrevient_tv", R.drawable.telegram),
            SocialLink("Email", "contact@jesusrevient.tv", R.drawable.mail),
            SocialLink("GPS", "https://maps.app.goo.gl/SFS46YG9JsMCuCW27", R.drawable.gps),
            SocialLink("Application Android", "https://play.google.com/store/apps/details?id=com.maougnonjesusrevient.jesus_revient_tv&pli=1", R.drawable.app),
        )

        val recyclerView: RecyclerView = findViewById(R.id.socialLinksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SocialLinksAdapter(socialLinks)
    }
}
