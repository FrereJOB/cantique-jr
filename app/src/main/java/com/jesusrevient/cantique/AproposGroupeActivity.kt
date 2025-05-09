package com.example.cantiqueapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AproposGroupeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apropos_groupe)

        val description = findViewById<TextView>(R.id.text_info_groupe)
        description.text = "Le groupe 'Jésus Revient' est une communauté chrétienne engagée dans la louange et l'évangélisation à travers la musique."

        setupLink(R.id.link_site, "https://jesusrevient.org")
        setupLink(R.id.link_facebook_page, "https://facebook.com/jesusrevientpage")
        setupLink(R.id.link_facebook_group, "https://facebook.com/groups/jesusrevient")
        setupLink(R.id.link_whatsapp, "https://chat.whatsapp.com/invite/jesusrevient")
        setupLink(R.id.link_telegram_channel, "https://t.me/jesusrevient_channel")
        setupLink(R.id.link_telegram_group, "https://t.me/jesusrevient_group")
        setupLink(R.id.link_twitter, "https://twitter.com/jesusrevient")
        setupLink(R.id.link_gps, "geo:0,0?q=Eglise Jésus Revient")
    }

    private fun setupLink(id: Int, url: String) {
        val layout = findViewById<LinearLayout>(id)
        layout.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}