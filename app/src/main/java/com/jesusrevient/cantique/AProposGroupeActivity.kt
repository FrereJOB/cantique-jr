package com.jesusrevient.cantique

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesusrevient.cantique.databinding.ActivityAproposAppBinding

class AProposGroupeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAproposAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAproposAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "À propos du groupe"

        binding.textInfoApp.text = """
            Cette application est un recueil de chants chrétiens du groupe Jésus Revient.
            
            Elle a été développée par le frère JOB, membre de l'église Jésus Revient située à Yaoundé, Cameroun.
            
            Pour toute suggestion ou amélioration, vous pouvez contacter :
            Email : mbargagastonmagloire@gmail.com
            
            Que le Seigneur vous bénisse richement !
        """.trimIndent()
    }
}
