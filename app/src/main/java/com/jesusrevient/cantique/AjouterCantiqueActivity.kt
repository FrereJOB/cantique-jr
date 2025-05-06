package com.jesusrevient.cantique

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jesusrevient.cantique.databinding.ActivityAjouterCantiqueBinding
import com.jesusrevient.cantique.models.Song
import java.text.SimpleDateFormat
import java.util.*

class AjouterCantiqueActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAjouterCantiqueBinding
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAjouterCantiqueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Ajouter un cantique"

        binding.editDateAjout.setOnClickListener {
            DatePickerDialog(this,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    binding.editDateAjout.setText(sdf.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnEnregistrer.setOnClickListener {
            val song = Song(
                titre = binding.editTitre.text.toString(),
                auteur = binding.editAuteur.text.toString(),
                paroles = binding.editParoles.text.toString(),
                numero = binding.editNumero.text.toString().toIntOrNull() ?: 0,
                categorie = binding.editCategorie.text.toString(),
                dateAjout = binding.editDateAjout.text.toString(),
                audioUrl = "", // à remplacer après l’upload
                partitionPdfUrl = "" // à remplacer après l’upload
            )

            db.collection("cantique")
                .add(song)
                .addOnSuccessListener {
                    Toast.makeText(this, "Cantique ajouté avec succès", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erreur : ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
