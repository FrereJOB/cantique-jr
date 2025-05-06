package com.jesusrevient.cantique

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class SupprimerCantiqueActivity : AppCompatActivity() {

    private lateinit var numeroInput: EditText
    private lateinit var btnSupprimer: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supprimer_cantique)

        numeroInput = findViewById(R.id.editTextNumeroSupprimer)
        btnSupprimer = findViewById(R.id.btnSupprimer)

        btnSupprimer.setOnClickListener {
            val numero = numeroInput.text.toString().trim()

            if (numero.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer le numéro du cantique.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val docRef = db.collection("cantique").whereEqualTo("numero", numero)
            docRef.get().addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val docId = documents.first().id

                    AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Êtes-vous sûr de vouloir supprimer ce cantique ?")
                        .setPositiveButton("Oui") { _, _ ->
                            db.collection("cantique").document(docId).delete()
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Cantique supprimé avec succès.", Toast.LENGTH_SHORT).show()
                                    numeroInput.text.clear()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Échec de la suppression.", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .setNegativeButton("Non", null)
                        .show()

                } else {
                    Toast.makeText(this, "Aucun cantique trouvé avec ce numéro.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Erreur lors de la recherche.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
