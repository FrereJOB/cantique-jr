package com.jesusrevient.cantique.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.jesusrevient.cantique.models.Song
import com.google.firebase.firestore.ktx.toObject

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private val songsCollection = db.collection("cantiques")

    // Récupérer tous les chants
    fun getAllSongs(onSuccess: (List<Song>) -> Unit, onFailure: (Exception) -> Unit): ListenerRegistration {
        return songsCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                onFailure(exception)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val songs = snapshot.documents.mapNotNull { it.toObject<Song>() }
                onSuccess(songs)
            }
        }
    }

    // Ajouter un chant
    fun addSong(song: Song, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        songsCollection.add(song)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    // Supprimer un chant par ID
    fun deleteSong(songId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        songsCollection.document(songId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    // Mettre à jour un chant par ID
    fun updateSong(songId: String, updatedSong: Song, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        songsCollection.document(songId)
            .set(updatedSong)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }
}
