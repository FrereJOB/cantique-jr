package com.jesusrevient.cantique.models

data class Song(
    val id: String = "",
    val titre: String = "",
    val auteur: String = "",
    val paroles: String = "",
    val categorie: String = "",
    val numero: Int = 0,
    val audioUrl: String = "",
    val partitionPdfUrl: String = ""
)
