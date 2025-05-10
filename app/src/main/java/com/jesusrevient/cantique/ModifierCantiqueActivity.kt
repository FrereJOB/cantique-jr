package com.jesusrevient.cantique

import android.content.Intent
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
            val numero = numeroInput.text.toString().trim()
            if (numero.isNotEmpty()) {
                rechercherCantique(numero)
            } else {
                Toast.makeText(this, "Veuillez entrer un numéro", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, "Aucun cantique trouvé avec ce numéro", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erreur lors de la recherche", Toast.LENGTH_SHORT).show()
            }
    }

    private fun modifierCantique() {
        val id = songId
        if (id == null) {
            Toast.makeText(this, "Veuillez rechercher un cantique d'abord", Toast.LENGTH_SHORT).show()
            return
        }

        val titre = titreInput.text.toString().trim()
        val auteur = auteurInput.text.toString().trim()
        val categorie = categorieInput.text.toString().trim()
        val paroles = parolesInput.text.toString().trim()

        if (titre.isEmpty() || auteur.isEmpty() || categorie.isEmpty() || paroles.isEmpty()) {
            Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedSong = mapOf(
            "titre" to titre,
            "auteur" to auteur,
            "categorie" to categorie,
            "paroles" to paroles
        )

        db.collection("cantiques").document(id)
            .update(updatedSong)
            .addOnSuccessListener {
                Toast.makeText(this, "Cantique modifié avec succès", Toast.LENGTH_SHORT).show()
                // Retour à l'écran d'administration
                startActivity(Intent(this, AdminDashboardActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Échec de la modification", Toast.LENGTH_SHORT).show()
            }
    }
}
