data class Song(
    var numero: Int = 0,
    var titre: String = "",
    var categorie: String = "",
    var auteur: String = "",
    var paroles: String = "",
    var audioUrl: String = "",
    var partitionPdfUrl: String = "",
    var dateAjout: com.google.firebase.Timestamp? = null
) {
    constructor() : this(0, "", "", "", "", "", "", null)
}
