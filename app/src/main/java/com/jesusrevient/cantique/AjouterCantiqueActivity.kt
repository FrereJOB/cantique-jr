package com.jesusrevient.cantique

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var spinnerCollection: Spinner

    private var selectedCollection: String = "cantiques"

    private var audioUri: Uri? = null
    private var pdfUri: Uri? = null

    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val REQUEST_AUDIO = 100
    private val REQUEST_PDF = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_cantique)

        // Rediriger si non connecté
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Initialisation des vues
        editTitre = findViewById(R.id.editTitre)
        editAuteur = findViewById(R.id.editAuteur)
        editCategorie = findViewById(R.id.editCategorie)
        editNumero = findViewById(R.id.editNumero)
        editParoles = findViewById(R.id.editParoles)
        buttonChooseAudio = findViewById(R.id.buttonChooseAudio)
        buttonChoosePdf = findViewById(R.id.buttonChoosePdf)
        buttonAjouter = findViewById(R.id.buttonAjouter)
        spinnerCollection = findViewById(R.id.spinnerCollection)

        // Configuration du Spinner
        val collections = arrayOf("cantiques", "voies_eternel", "chants_victoire")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, collections)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCollection.adapter = adapterSpinner

        spinnerCollection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCollection = collections[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedCollection = "cantiques"
            }
        }

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
            val numeroStr = editNumero.text.toString().trim()
            val paroles = editParoles.text.toString().trim()

            if (titre.isBlank() || auteur.isBlank() || categorie.isBlank() ||
                numeroStr.isBlank() || paroles.isBlank()) {
                Toast.makeText(this, "Tous les champs (sauf audio et PDF) sont requis", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val numero = numeroStr.toIntOrNull()
            if (numero == null) {
                Toast.makeText(this, "Le numéro doit être un entier valide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            uploadFilesAndSave(titre, auteur, categorie, numero, paroles)
        }
    }

    private fun uploadFilesAndSave(
        titre: String,
        auteur: String,
        categorie: String,
        numero: Int,
        paroles: String
    ) {
        val chant = mutableMapOf<String, Any>(
            "titre" to titre,
            "auteur" to auteur,
            "categorie" to categorie,
            "numero" to numero,
            "paroles" to paroles,
            "dateAjout" to Date()
        )

        val storageRef = storage.reference
        val uploadTasks = mutableListOf<com.google.android.gms.tasks.Task<Uri>>()

        if (audioUri != null) {
            val audioRef = storageRef.child("audios/${UUID.randomUUID()}.mp3")
            val uploadAudioTask = audioRef.putFile(audioUri!!)
                .continueWithTask { task ->
                    if (!task.isSuccessful) throw task.exception ?: Exception("Erreur upload audio")
                    audioRef.downloadUrl
                }.addOnSuccessListener { uri ->
                    chant["audioUrl"] = uri.toString()
                }.addOnFailureListener {
                    Toast.makeText(this, "Échec upload audio", Toast.LENGTH_SHORT).show()
                }
            uploadTasks.add(uploadAudioTask)
        }

        if (pdfUri != null) {
            val pdfRef = storageRef.child("pdfs/${UUID.randomUUID()}.pdf")
            val uploadPdfTask = pdfRef.putFile(pdfUri!!)
                .continueWithTask { task ->
                    if (!task.isSuccessful) throw task.exception ?: Exception("Erreur upload PDF")
                    pdfRef.downloadUrl
                }.addOnSuccessListener { uri ->
                    chant["pdfUrl"] = uri.toString()
                }.addOnFailureListener {
                    Toast.makeText(this, "Échec upload PDF", Toast.LENGTH_SHORT).show()
                }
            uploadTasks.add(uploadPdfTask)
        }

        if (uploadTasks.isNotEmpty()) {
            com.google.android.gms.tasks.Tasks.whenAllComplete(uploadTasks)
                .addOnSuccessListener {
                    saveToFirestore(chant)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erreur lors de l'envoi des fichiers", Toast.LENGTH_LONG).show()
                }
        } else {
            saveToFirestore(chant)
        }
    }

    private fun saveToFirestore(chant: Map<String, Any>) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Toast.makeText(this, "Vous devez être connecté pour ajouter un cantique", Toast.LENGTH_LONG).show()
            return
        }

        firestore.collection(selectedCollection)
            .add(chant)
            .addOnSuccessListener {
                Toast.makeText(this, "Cantique ajouté avec succès", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erreur: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_AUDIO -> audioUri = data.data
                REQUEST_PDF -> pdfUri = data.data
            }
        }
    }
}
