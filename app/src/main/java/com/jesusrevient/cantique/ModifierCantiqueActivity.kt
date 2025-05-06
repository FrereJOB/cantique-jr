package com.jesusrevient.cantique

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.jesusrevient.cantique.models.Song

class ModifierCantiqueActivity : AppCompatActivity() {

    private lateinit var numeroInput: EditText
    private lateinit var btnRechercher: Button
    private lateinit var titreInput: EditText
    private lateinit var auteurInput: EditText
    private lateinit var categorieInput: EditText
    private lateinit var parolesInput: EditText
    private lateinit var btnEnregistrer: Button

    private val db = FirebaseFirestore.getInstance()
    private var songId: String? = null  // pour stocker l'ID du document à modifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifier_cantique)

        numeroInput = findViewById(R.id.editTextNumero)
        btnRechercher = findViewById(R.id.btnRechercher)
        titreInput = findViewById(R.id.editTextTitre)
        auteurInput = findViewById(R.id.editTextAuteur)
        categorieInput = findViewById(R.id.editTextCategorie)
        parolesInput = findViewById(R.id.editTextParoles)
        btnEnregistrer = findViewById(R.id.btnEnregistrer)

        btnRechercher.setOnClickListener {
            val numero = numeroInput.text.toString()
            if (numero.isNotEmpty()) {
                rechercherCantique(numero)
            }
        }

        btnEnregistrer.setOnClickListener {
            modifierCantique()
        }
    }

    private fun rechercherCantique(numero: String) {
        db.collection("cantique")
            .whereEqualTo("numero", numero)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val doc = documents.first()
                    val song = doc.toObject(Song::class.java)
                    songId = doc.id

                    titreInput.setText(song.titre)
                    auteurInput.setText(song.auteur)
                    categorieInput.setText(song.categorie)
                    parolesInput.setText(song.paroles)
                } else {
                    Toast.makeText(this, "Aucun cantique trouvé", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erreur de recherche", Toast.LENGTH_SHORT).show()
            }
    }

    private fun modifierCantique() {
        val id = songId ?: return
        val updatedSong = mapOf(
            "titre" to titreInput.text.toString(),
            "auteur" to auteurInput.text.toString(),
            "categorie" to categorieInput.text.toString(),
            "paroles" to parolesInput.text.toString()
        )

        db.collection("cantique").document(id)
            .update(updatedSong)
            .addOnSuccessListener {
                Toast.makeText(this, "Cantique modifié avec succès", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Échec de la modification", Toast.LENGTH_SHORT).show()
            }
    }
}
