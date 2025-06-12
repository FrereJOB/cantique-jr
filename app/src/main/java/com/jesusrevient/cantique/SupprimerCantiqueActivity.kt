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

            db.collection("cantiques")
                .whereEqualTo("numero", numero)
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
                                db.collection("cantiques").document(docId).delete()
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
