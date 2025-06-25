package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SupprimerCantiqueActivity : AppCompatActivity() {

    private lateinit var numeroInput: EditText
    private lateinit var btnSupprimer: Button
    private lateinit var spinnerCollection: Spinner

    private val db = FirebaseFirestore.getInstance()
    private var selectedCollection = "cantiques"  // valeur par défaut

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supprimer_cantique)

        numeroInput = findViewById(R.id.editTextNumeroSupprimer)
        btnSupprimer = findViewById(R.id.btnSupprimer)
        spinnerCollection = findViewById(R.id.spinnerCollection)

        // Initialiser le Spinner avec les noms des collections
        val collections = listOf("cantiques", "voies_eternel", "chants_victoire")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, collections)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCollection.adapter = adapter

        spinnerCollection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedCollection = collections[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnSupprimer.setOnClickListener {
            val numero = numeroInput.text.toString().trim()

            if (numero.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer le numéro du cantique.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val numeroInt = numero.toIntOrNull()
            if (numeroInt == null) {
                Toast.makeText(this, "Le numéro doit être un entier valide.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.collection(selectedCollection)
                .whereEqualTo("numero", numeroInt)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val doc = documents.first()
                        val docId = doc.id
                        val titre = doc.getString("titre") ?: "ce cantique"

                        AlertDialog.Builder(this)
                            .setTitle("Confirmer la suppression")
                            .setMessage("Voulez-vous vraiment supprimer \"$titre\" (N° $numero) ?")
                            .setPositiveButton("Oui") { _, _ ->
                                db.collection(selectedCollection).document(docId).delete()
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Cantique supprimé avec succès.", Toast.LENGTH_SHORT).show()
                                        numeroInput.text.clear()
                                        startActivity(Intent(this, AdminDashboardActivity::class.java))
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "Erreur lors de la suppression.", Toast.LENGTH_SHORT).show()
                                    }
                            }
                            .setNegativeButton("Non", null)
                            .show()
                    } else {
                        Toast.makeText(this, "Aucun cantique trouvé avec ce numéro.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erreur lors de la recherche.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
