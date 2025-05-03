data class Song(
    val numero: Int = 0,
    val titre: String = "",
    val categorie: String = "",
    val hauteur: String = "",
    val paroles: String = "",
    val audioUrl: String = "",
    val partitionPdfUrl: String = "",
    val dateAjout: com.google.firebase.Timestamp? = null
)
