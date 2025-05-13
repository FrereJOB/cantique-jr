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

    // Étape 1 : gestion des uploads
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

    // Étape 2 : attendre tous les uploads (s’il y en a) puis enregistrer dans Firestore
    if (uploadTasks.isNotEmpty()) {
        com.google.android.gms.tasks.Tasks.whenAllComplete(uploadTasks)
            .addOnSuccessListener {
                saveToFirestore(chant)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erreur lors de l'envoi des fichiers", Toast.LENGTH_LONG).show()
            }
    } else {
        // Aucun fichier à uploader
        saveToFirestore(chant)
    }
}

private fun saveToFirestore(chant: Map<String, Any>) {
    firestore.collection("cantiques")
        .add(chant)
        .addOnSuccessListener {
            Toast.makeText(this, "Cantique ajouté avec succès", Toast.LENGTH_SHORT).show()
            finish() // Fermer l'activité
        }
        .addOnFailureListener {
            Toast.makeText(this, "Erreur lors de l'ajout du cantique", Toast.LENGTH_LONG).show()
        }
}
