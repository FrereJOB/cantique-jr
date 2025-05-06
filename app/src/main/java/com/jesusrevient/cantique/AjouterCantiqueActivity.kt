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

    private lateinit var titreEditText: EditText
    private lateinit var auteurEditText: EditText
    private lateinit var parolesEditText: EditText
    private lateinit var btnSelectAudio: Button
    private lateinit var btnSelectPdf: Button
    private lateinit var btnUpload: Button

    private var audioUri: Uri? = null
    private var pdfUri: Uri? = null

    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    companion object {
        private const val PICK_AUDIO_REQUEST = 1001
        private const val PICK_PDF_REQUEST = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_cantique)

        titreEditText = findViewById(R.id.titreEditText)
        auteurEditText = findViewById(R.id.auteurEditText)
        parolesEditText = findViewById(R.id.parolesEditText)
        btnSelectAudio = findViewById(R.id.btnSelectAudio)
        btnSelectPdf = findViewById(R.id.btnSelectPdf)
        btnUpload = findViewById(R.id.btnUpload)

        btnSelectAudio.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            startActivityForResult(intent, PICK_AUDIO_REQUEST)
        }

        btnSelectPdf.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, PICK_PDF_REQUEST)
        }

        btnUpload.setOnClickListener {
            uploadCantique()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_AUDIO_REQUEST -> audioUri = data?.data
                PICK_PDF_REQUEST -> pdfUri = data?.data
            }
        }
    }

    private fun uploadCantique() {
        val titre = titreEditText.text.toString().trim()
        val auteur = auteurEditText.text.toString().trim()
        val paroles = parolesEditText.text.toString().trim()

        if (titre.isEmpty() || audioUri == null || pdfUri == null) {
            Toast.makeText(this, "Veuillez remplir tous les champs et sélectionner les fichiers", Toast.LENGTH_SHORT).show()
            return
        }

        val audioRef = storage.reference.child("audios/${UUID.randomUUID()}.mp3")
        val pdfRef = storage.reference.child("partitions/${UUID.randomUUID()}.pdf")

        audioRef.putFile(audioUri!!)
            .addOnSuccessListener { audioTask ->
                audioRef.downloadUrl.addOnSuccessListener { audioUrl ->
                    pdfRef.putFile(pdfUri!!)
                        .addOnSuccessListener {
                            pdfRef.downloadUrl.addOnSuccessListener { pdfUrl ->
                                val cantique = hashMapOf(
                                    "titre" to titre,
                                    "auteur" to auteur,
                                    "paroles" to paroles,
                                    "audioUrl" to audioUrl.toString(),
                                    "partitionPdfUrl" to pdfUrl.toString(),
                                    "dateAjout" to Date()
                                )

                                firestore.collection("cantique")
                                    .add(cantique)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Cantique ajouté avec succès", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Erreur lors de l'ajout", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Échec de l’envoi de l’audio", Toast.LENGTH_SHORT).show()
            }
    }
}
