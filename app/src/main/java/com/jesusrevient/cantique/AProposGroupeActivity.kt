package com.jesusrevient.cantique

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
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
            <h2 style="color:#4E342E;">JESUS-REVIENT TV</h2>
            
            <b>Description</b><br/>
            Mouvement international d'évangélisation prêchant la repentance, la sanctification et la doctrine biblique pour préparer les saints à l'Enlèvement.<br/><br/>
            
            <b>Siège :</b><br/>
            Bénin, Dekoungbé, Hédomey (200m après la Pharmacie Hédomey).<br/>
            <b>Tél. :</b> (+229) 97 25 35 39 / 94 33 69 05<br/>
            <b>Chaîne TV :</b> JESUS-REVIENT.TV (Satellite Amos 17, décodeur Strong)<br/><br/>

            <b>Nous suivre :</b><br/>
            - <a href="https://jesusrevient.tv">Site web</a><br/>
            - <a href="https://facebook.com/groupejesusrevient">Page Facebook</a><br/>
            - <a href="https://facebook.com/groups/jesusrevient.tv">Groupe Facebook</a><br/>
            - <a href="https://www.whatsapp.com/channel/0029VaDKtleHgZWfIv4SNW3z">Chaîne WhatsApp</a><br/>
            - <a href="https://t.me/jesusrevient">Canal Telegram</a><br/>
            - <a href="https://t.me/jesusrevient_tv">Groupe Telegram</a><br/>
            - <a href="https://twitter.com/jesusrevienttv">Twitter</a><br/>
            - <a href="https://maps.app.goo.gl/SFS46YG9JsMCuCW27">Localisation GPS</a><br/>
            - <a href="https://play.google.com/store/apps/details?id=com.maougnonjesusrevient.jesus_revient_tv">Application Android</a><br/>
        """.trimIndent()

        binding.textInfoGroupe.text = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
        binding.textInfoGroupe.movementMethod = LinkMovementMethod.getInstance()
    }
}
