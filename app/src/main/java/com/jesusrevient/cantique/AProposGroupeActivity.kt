<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FFFFFF">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="20dp">

        <!-- Titre principal -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Cantique Jésus-Revient"
            android:textSize="24sp"
            android:textStyle="bold"
            android:textColor="#1E3A8A"
            android:gravity="center"
            android:paddingBottom="16dp" />

        <!-- Description principale -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Cantique Jésus-Revient est une application mobile Android dédiée à la gestion et à la diffusion des cantiques chrétiens produits par le groupe Jésus-Revient. Cette application permet de consulter, écouter, télécharger et gérer des chants inspirés de la foi chrétienne, dans le but de soutenir la louange, l'adoration et l'édification spirituelle au sein du corps de Christ."
            android:textSize="16sp"
            android:textColor="#444444"
            android:lineSpacingExtra="6dp"
            android:paddingBottom="16dp" />

        <!-- Sous-titre -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Fonctionnalités principales"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="#1E3A8A"
            android:paddingBottom="8dp" />

        <!-- Fonctionnalités -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="• Catalogue de cantiques : Accès à une bibliothèque organisée de chants chrétiens avec titre, numéro, auteur, paroles et audio.\n\n• Lecture audio : Écoute directe des cantiques hébergés sur Firebase Storage.\n\n• Partitions PDF : Affichage et téléchargement des partitions associées à chaque cantique.\n\n• Mode hors ligne : Téléchargement local des chants pour usage sans connexion Internet.\n\n• Menu latéral (drawer) :\n   - À propos du groupe\n   - À propos de l’application\n   - Partage de l’application\n   - Accès Admin sécurisé"
            android:textSize="15sp"
            android:textColor="#444444"
            android:lineSpacingExtra="6dp"
            android:paddingBottom="16dp" />

        <!-- Espace Admin -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Espace Admin (restreint) :"
            android:textStyle="bold"
            android:textSize="16sp"
            android:textColor="#1E3A8A"
            android:paddingBottom="4dp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="• Authentification par Firebase Authentication (Email/Mot de passe)\n• Ajout, modification et suppression de cantiques\n• Tableau de bord simple et épuré pour la gestion"
            android:textSize="15sp"
            android:textColor="#444444"
            android:lineSpacingExtra="6dp"
            android:paddingBottom="16dp" />

        <!-- Technologies -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Technologies utilisées"
            android:textStyle="bold"
            android:textSize="16sp"
            android:textColor="#1E3A8A"
            android:paddingBottom="4dp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="• Kotlin / Android Studio\n• Firebase :\n   - Firestore pour la base de données\n   - Firebase Storage pour les audios et PDF\n   - Firebase Authentication pour la connexion Admin"
            android:textSize="15sp"
            android:textColor="#444444"
            android:lineSpacingExtra="6dp"
            android:paddingBottom="16dp" />

        <!-- Structure de données -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Structure de la base de données Firestore"
            android:textStyle="bold"
            android:textSize="16sp"
            android:textColor="#1E3A8A"
            android:paddingBottom="4dp" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="{\n  \"titre\": \"Exemple de cantique\",\n  \"auteur\": \"Nom de l'auteur\",\n  \"numero\": 1,\n  \"categorie\": \"Adoration\",\n  \"audioUrl\": \"https://...\",\n  \"partitionPdfUrl\": \"https://...\",\n  \"dateAjout\": \"2025-05-01T12:00:00Z\",\n  \"paroles\": \"Paroles complètes du cantique...\"\n}"
            android:typeface="monospace"
            android:textSize="14sp"
            android:textColor="#333333"
            android:background="#F2F2F2"
            android:padding="10dp"
            android:layout_marginBottom="24dp" />

        <!-- Pied de page -->
        <TextView
            android:id="@+id/footer"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="La Trompète va sonner"
            android:textSize="16sp"
            android:textStyle="bold"
            android:textColor="#FFD700"
            android:gravity="center"
            android:background="#1E3A8A"
            android:padding="12dp"
            android:layout_marginTop="8dp" />
    </LinearLayout>
</ScrollView>
