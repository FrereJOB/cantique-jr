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

**Liens** :  
- Facebook : facebook.com/groupejesusrevient  
- Groupe FB : facebook.com/groups/jesusrevient.tv  
- WhatsApp : whatsapp.com/channel/0029VaDKtIeHgZWfIv4S...  
- Telegram : t.me/jesusrevient_tv  
- Twitter : twitter.com/jesusrevienttv  
- YouTube : youtube.com/@JESUSREVIENTTV  
- Site : jesusrevient.tv  
- GPS : maps.app.goo.gl/SFS46YG9JsMCuCW27  
- Email : contact@jesusrevient.tv  
- App Android : play.google.com/store/apps/details?id=com.mao...  

---  
*Version concise, conserve l'essentiel.* vous bénisse abondamment !
        """.trimIndent()
    }
}
