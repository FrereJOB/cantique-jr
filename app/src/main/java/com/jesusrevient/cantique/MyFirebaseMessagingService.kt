package com.jesusrevient.cantique

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Nouveau token : $token")
        // Ici tu peux envoyer le token à ton serveur si tu veux l’enregistrer
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FCM", "Message reçu de : ${message.from}")
        message.notification?.let {
            Log.d("FCM", "Notification : ${it.title} - ${it.body}")
            // Ici tu peux déclencher une notification personnalisée si besoin
        }
    }
}
