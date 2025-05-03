package com.jesusrevient.cantique.models

data class Song(
    val id: String = "",
    val number: Int = 0,
    val title: String = "",
    val lyrics: String = "",
    val category: String = "",
    val audioUrl: String = "",
    val sheetUrl: String = ""
)
