package com.jesusrevient.cantique.models

import com.google.firebase.Timestamp

data class Song(
    var numero: Int = 0,
    var titre: String = "",
    var categorie: String = "",
    var auteur: String = "",
    var paroles: String = "",
    var audioUrl: String? = null,
    var partitionPdfUrl: String? = null,
    var dateAjout: Timestamp? = null
) {
    constructor() : this(0, "", "", "", "", null, null, null)
}
