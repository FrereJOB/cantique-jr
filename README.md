# Cantique Jésus-Revient

**Cantique Jésus-Revient** est une application mobile Android dédiée à la gestion et à la diffusion des cantiques chrétiens produits par le groupe **Jésus-Revient**. Cette application permet de consulter, écouter, télécharger et gérer des chants inspirés de la foi chrétienne, dans le but de soutenir la louange, l'adoration et l'édification spirituelle au sein du corps de Christ.

## Vision du groupe Jésus-Revient

Le groupe **Jésus-Revient** a pour vocation de répandre l’Évangile de Jésus-Christ par la musique, les cantiques inspirés et les enseignements bibliques. À travers cette application, nous souhaitons rendre accessibles nos chants à un large public, même sans connexion Internet, tout en proposant une interface conviviale et fidèle à l’esprit du service pour Dieu.

---

## Fonctionnalités principales

- **Catalogue de cantiques** : Accès à une bibliothèque organisée de chants chrétiens avec titre, numéro, auteur, paroles et audio.
- **Lecture audio** : Écoute directe des cantiques hébergés sur Firebase Storage.
- **Partitions PDF** : Affichage et téléchargement des partitions associées à chaque cantique.
- **Mode hors ligne** : Téléchargement local des chants pour usage sans connexion Internet.
- **Menu latéral (drawer)** :
  - À propos du groupe
  - À propos de l’application
  - Partage de l’application
  - Accès Admin sécurisé

- **Espace Admin (restreint)** :
  - Authentification par Firebase Authentication (Email/Mot de passe)
  - Ajout, modification et suppression de cantiques
  - Tableau de bord simple et épuré pour la gestion

---

## Technologies utilisées

- **Kotlin / Android Studio**
- **Firebase** :
  - Firestore pour la base de données
  - Firebase Storage pour les audios et PDF
  - Firebase Authentication pour la connexion Admin

---

## Structure de la base de données Firestore

Chaque document de cantique contient les champs suivants :

```json
{
  "titre": "Exemple de cantique",
  "auteur": "Nom de l'auteur",
  "numero": 1,
  "categorie": "Adoration",
  "audioUrl": "https://...",
  "partitionPdfUrl": "https://...",
  "dateAjout": "2025-05-01T12:00:00Z",
  "paroles": "Paroles complètes du cantique..."
}
