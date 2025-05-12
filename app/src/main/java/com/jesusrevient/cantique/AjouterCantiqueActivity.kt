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
            val titre = editTitre.text.toString().trim()
            val auteur = editAuteur.text.toString().trim()
            val categorie = editCategorie.text.toString().trim()
            val numero = editNumero.text.toString().trim()
            val paroles = editParoles.text.toString().trim()

            if (titre.isBlank() || auteur.isBlank() || categorie.isBlank() ||
                numero.isBlank() || paroles.isBlank()
            ) {
                Toast.makeText(this, "Tous les champs (sauf audio et PDF) sont requis", Toast.LENGTH_SHORT).show()
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
        val chant = hashMapOf(
            "titre" to titre,
            "auteur" to auteur,
            "categorie" to categorie,
            "numero" to numero.toIntOrNull() ?: 0,
            "paroles" to paroles,
            "dateAjout" to Date()
        )

        fun saveToFirestore() {
            firestore.collection("cantiques")
                .add(chant)
                .addOnSuccessListener {
                    Toast.makeText(this, "Cantique ajouté avec succès", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, AdminDashboardActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erreur d'ajout dans Firestore", Toast.LENGTH_LONG).show()
                }
        }

        if (audioUri != null) {
            val audioRef = storage.reference.child("audios/${UUID.randomUUID()}.mp3")
            audioRef.putFile(audioUri!!).addOnSuccessListener {
                audioRef.downloadUrl.addOnSuccessListener { audioUrl ->
                    chant["audioUrl"] = audioUrl.toString()

                    if (pdfUri != null) {
                        val pdfRef = storage.reference.child("partitions/${UUID.randomUUID()}.pdf")
                        pdfRef.putFile(pdfUri!!).addOnSuccessListener {
                            pdfRef.downloadUrl.addOnSuccessListener { pdfUrl ->
                                chant["partitionPdfUrl"] = pdfUrl.toString()
                                saveToFirestore()
                            }.addOnFailureListener {
                                Toast.makeText(this, "Échec du téléchargement du PDF", Toast.LENGTH_LONG).show()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(this, "Échec de l'envoi du fichier PDF", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        saveToFirestore()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Échec du téléchargement de l'audio", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Échec de l'envoi du fichier audio", Toast.LENGTH_LONG).show()
            }
        } else if (pdfUri != null) {
            val pdfRef = storage.reference.child("partitions/${UUID.randomUUID()}.pdf")
            pdfRef.putFile(pdfUri!!).addOnSuccessListener {
                pdfRef.downloadUrl.addOnSuccessListener { pdfUrl ->
                    chant["partitionPdfUrl"] = pdfUrl.toString()
                    saveToFirestore()
                }.addOnFailureListener {
                    Toast.makeText(this, "Échec du téléchargement du PDF", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Échec de l'envoi du fichier PDF", Toast.LENGTH_LONG).show()
            }
        } else {
            saveToFirestore()
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
