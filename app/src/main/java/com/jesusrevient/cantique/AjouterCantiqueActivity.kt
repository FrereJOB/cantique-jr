package com.jesusrevient.cantique

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AjouterCantiqueActivity : AppCompatActivity() {

    private lateinit var editTitre: EditText
    private lateinit var editAuteur: EditText
    private lateinit var editCategorie: EditText
    private lateinit var editNumero: EditText
    private lateinit var editParoles: EditText
    private lateinit var buttonChooseAudio: Button
    private lateinit var buttonChoosePdf: Button
    private lateinit var buttonAjouter: Button

    private var audioUri: Uri? = null
    private var pdfUri: Uri? = null

    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val REQUEST_AUDIO = 100
    private val REQUEST_PDF = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_cantique)

        editTitre = findViewById(R.id.editTitre)
        editAuteur = findViewById(R.id.editAuteur)
        editCategorie = findViewById(R.id.editCategorie)
        editNumero = findViewById(R.id.editNumero)
        editParoles = findViewById(R.id.editParoles)
        buttonChooseAudio = findViewById(R.id.buttonChooseAudio)
        buttonChoosePdf = findViewById(R.id.buttonChoosePdf)
        buttonAjouter = findViewById(R.id.buttonAjouter)

        buttonChooseAudio.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            startActivityForResult(intent, REQUEST_AUDIO)
        }

        buttonChoosePdf.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, REQUEST_PDF)
        }

        buttonAjouter.setOnClickListener {
            val titre = editTitre.text.toString()
            val auteur = editAuteur.text.toString()
            val categorie = editCategorie.text.toString()
            val numero = editNumero.text.toString()
            val paroles = editParoles.text.toString()

            if (titre.isBlank() || audioUri == null || pdfUri == null) {
                Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            uploadFilesAndSave(titre, auteur, categorie, numero, paroles)
        }
    }

    private fun uploadFilesAndSave(
        titre: String,
        auteur: String,
        categorie: String,
        numero: String,
        paroles: String
    ) {
        val audioRef = storage.reference.child("audios/${UUID.randomUUID()}.mp3")
        val pdfRef = storage.reference.child("partitions/${UUID.randomUUID()}.pdf")

        audioUri?.let { audio ->
            audioRef.putFile(audio).addOnSuccessListener { audioTask ->
                audioRef.downloadUrl.addOnSuccessListener { audioUrl ->

                    pdfUri?.let { pdf ->
                        pdfRef.putFile(pdf).addOnSuccessListener {
                            pdfRef.downloadUrl.addOnSuccessListener { pdfUrl ->

                                val chant = hashMapOf(
                                    "titre" to titre,
                                    "auteur" to auteur,
                                    "categorie" to categorie,
                                    "numero" to numero,
                                    "paroles" to paroles,
                                    "audioUrl" to audioUrl.toString(),
                                    "partitionPdfUrl" to pdfUrl.toString(),
                                    "dateAjout" to Date()
                                )

                                firestore.collection("cantique")
                                    .add(chant)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Cantique ajouté avec succès", Toast.LENGTH_LONG).show()
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Erreur d'ajout dans Firestore", Toast.LENGTH_LONG).show()
                                    }

                            }
                        }
                    }

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_AUDIO -> {
                    audioUri = data.data
                    buttonChooseAudio.text = "Audio sélectionné"
                }
                REQUEST_PDF -> {
                    pdfUri = data.data
                    buttonChoosePdf.text = "PDF sélectionné"
                }
            }
        }
    }
}
