package com.jesusrevient.cantique

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesusrevient.cantique.adaptater.SocialLinksAdapter
import com.jesusrevient.cantique.models.SocialLink

class AProposGroupeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apropos_groupe)

        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()

        val socialLinks = listOf(
            SocialLink("Téléphone", "tel:+22997253539"),
            SocialLink("Téléphone", "tel:+22994336905"),
            SocialLink("Facebook", "https://www.facebook.com/EgliseInternationaleJesusRevient/"),
            SocialLink("YouTube", "https://www.youtube.com/@EgliseInternationaleJesusRevient"),
            SocialLink("WhatsApp", "https://wa.me/22994336905"),
            SocialLink("Telegram", "https://t.me/eglisejesusrevient"),
            SocialLink("Email", "mailto:eglisejesusrevient@gmail.com"),
            SocialLink("GPS", "https://maps.app.goo.gl/PvFtPtwzG9CJ9mcX9"),
            SocialLink("Android", "https://play.google.com/store/apps/details?id=com.jesusrevient.cantique")
        )

        val recyclerView: RecyclerView = findViewById(R.id.socialLinksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SocialLinksAdapter(socialLinks)
    }
}
