package com.jesusrevient.cantique

import android.os.Bundle
import android.text.method.LinkMovementMethod
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

        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()

        val socialLinks = listOf(
            SocialLink("Groupe Facebook", "https://www.facebook.com/EgliseInternationaleJesusRevient/", R.drawable.facebook),
            SocialLink("Chaine Facebook", "https://wa.me/22994336905", R.drawable.facebook),
            SocialLink("YouTube", "https://www.youtube.com/@EgliseInternationaleJesusRevient", R.drawable.youtube),
            SocialLink("WhatsApp", "https://wa.me/22994336905", R.drawable.whatsapp),
            SocialLink("Chaine Telegram", "https://wa.me/22994336905", R.drawable.telegram),
            SocialLink("Twitter", "https://wa.me/22994336905", R.drawable.twitter),
            SocialLink("Groupe Telegram", "https://t.me/eglisejesusrevient", R.drawable.telegram),
            SocialLink("Email", "mailto:eglisejesusrevient@gmail.com", R.drawable.mail),
            SocialLink("GPS", "https://maps.app.goo.gl/PvFtPtwzG9CJ9mcX9", R.drawable.gps),
            SocialLink("Application Android", "https://play.google.com/store/apps/details?id=com.jesusrevient.cantique", R.drawable.app),
        )

        val recyclerView: RecyclerView = findViewById(R.id.socialLinksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SocialLinksAdapter(socialLinks)
    }
}
