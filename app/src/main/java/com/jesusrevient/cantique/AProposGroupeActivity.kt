package com.jesusrevient.cantique

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesusrevient.cantique.databinding.ActivityAproposGroupeBinding

class AProposGroupeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAproposGroupeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAproposGroupeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "À propos du groupe"

        binding.textInfoGroupe.text = """
            **JESUS-REVIENT TV**  

**Description**  
Mouvement international d'évangélisation prêchant la repentance, la sanctification et la doctrine biblique pour préparer les saints à l'Enlèvement.  

**Siège** :  
Bénin, Dekoungbé, Hédomey (200m après la Pharmacie Hédomey).  
**Tél.** : (+229) 97 25 35 39 / 94 33 69 05  
**Chaîne TV** : "JESUS-REVIENT.TV" (Satellite Amos 17, décodeur Strong).  


<contenu>
    <titre>Nous suivre</titre>
    <liens>
        <lien type="site" url="https://jesusrevient.tv" />
        <lien type="facebook" url="https://facebook.com/groupejesusrevient" />
        <lien type="Groupe Facebook" url="https:facebook.com/groups/jesusrevient.tv" />
        <lien type="facebook" url="https://facebook.com/groupejesusrevient" />
        <lien type="WhatsApp" url="https://wwww.whatsapp.com/channel/0029VaDKtleHgZWfIv4SNW3z" />
        <lien type="Canal Telegram" url="https://t.me/jesusrevient" />
        <lien type="Groupe Telegram" url="https://t.me/jesusrevient_tv" />
        <lien type="Twitter" url="https://twitter.com/jesusrevienttv" />
        <lien type="GPS" url="https://maps.app.goo.gl/SFS46YG9JsMCuCW27" />
        <lien type="Application" url="play.google.com/store/apps/details?id=com.maougnonjesusrevient.jesus_revient_tv" />


    </liens>
</contenu>

        """.trimIndent()
    }
}
