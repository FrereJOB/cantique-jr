package com.jesusrevient.cantique

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jesusrevient.cantique.models.Song
import java.util.*

class ModifierCantiqueActivity : AppCompatActivity() {

    private lateinit var numeroInput: EditText
    private lateinit var btnRechercher: Button
    private lateinit var titreInput: EditText
    private lateinit var auteurInput: EditText
    private lateinit var categorieInput: EditText
    private lateinit var parolesInput: EditText
    private lateinit var btnEnregistrer: Button
    private lateinit var audioButton: Button
    private lateinit var pdfButton: Button

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private var songId: String? = null
    private var audioUri: Uri? = null
    private var pdfUri: Uri? = null

    companion object {
        private const val AUDIO_REQUEST_CODE = 101
        private const val PDF_REQUEST_CODE = 102
    }

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
        audioButton = findViewById(R.id.btnAudio)
        pdfButton = findViewById(R.id.btnPdf)

        btnRechercher.setOnClickListener {
            val numero = numeroInput.text.toString().trim()
            if (numero.isNotEmpty()) {
                rechercherCantique(numero)
            } else {
                Toast.makeText(this, "Veuillez entrer un numéro", Toast.LENGTH_SHORT).show()
            }
        }

        audioButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            startActivityForResult(intent, AUDIO_REQUEST_CODE)
        }

        pdfButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, PDF_REQUEST_CODE)
        }

        btnEnregistrer.setOnClickListener {
            modifierCantique()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                AUDIO_REQUEST_CODE -> audioUri = data.data
                PDF_REQUEST_CODE -> pdfUri = data.data
            }
        }
    }

    private fun rechercherCantique(numero: String) {
        val numeroInt = numero.toIntOrNull()
if (numeroInt == null) {
    Toast.makeText(this, "Numéro invalide", Toast.LENGTH_SHORT).show()
    return
}

db.collection("cantiques")
    .whereEqualTo("numero", numeroInt)
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

        val updates = mutableMapOf<String, Any>(
            "titre" to titre,
            "auteur" to auteur,
            "categorie" to categorie,
            "paroles" to paroles
        )

        if (audioUri != null) {
            val audioRef = storage.reference.child("audios/${UUID.randomUUID()}.mp3")
            audioRef.putFile(audioUri!!)
                .addOnSuccessListener {
                    audioRef.downloadUrl.addOnSuccessListener { uri ->
                        updates["audioUrl"] = uri.toString()
                        uploadPdfIfNeededAndSave(id, updates)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Échec de l'envoi de l'audio", Toast.LENGTH_SHORT).show()
                }
        } else {
            uploadPdfIfNeededAndSave(id, updates)
        }
    }

    private fun uploadPdfIfNeededAndSave(id: String, updates: MutableMap<String, Any>) {
        if (pdfUri != null) {
            val pdfRef = storage.reference.child("partitions/${UUID.randomUUID()}.pdf")
            pdfRef.putFile(pdfUri!!)
                .addOnSuccessListener {
                    pdfRef.downloadUrl.addOnSuccessListener { uri ->
                        updates["partitionPdfUrl"] = uri.toString()
                        enregistrerModifications(id, updates)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Échec de l'envoi du PDF", Toast.LENGTH_SHORT).show()
                }
        } else {
            enregistrerModifications(id, updates)
        }
    }

    private fun enregistrerModifications(id: String, updates: Map<String, Any>) {
        db.collection("cantiques").document(id)
            .update(updates)
            .addOnSuccessListener {
                Toast.makeText(this, "Cantique modifié avec succès", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AdminDashboardActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Échec de la modification", Toast.LENGTH_SHORT).show()
            }
    }
}
