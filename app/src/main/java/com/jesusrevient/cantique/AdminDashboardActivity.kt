package com.jesusrevient.cantique

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import com.jesusrevient.cantique.BuildConfig

class AdminDashboardActivity : AppCompatActivity() {

    // Remplace par ta clé serveur FCM (Legacy key)
    private val FCM_SERVER_KEY = BuildConfig.FCM_SERVER_KEY
    private val FCM_API_URL = "https://fcm.googleapis.com/fcm/send"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        // 🔙 Activation du bouton retour
        findViewById<ImageButton>(R.id.back_button)?.setOnClickListener {
            finish()
        }

        // Boutons existants
        val btnAjouter = findViewById<Button>(R.id.btnAjouterCantique)
        val btnModifier = findViewById<Button>(R.id.btnModifierCantique)
        val btnSupprimer = findViewById<Button>(R.id.btnSupprimerCantique)

        btnAjouter.setOnClickListener {
            startActivity(Intent(this, AjouterCantiqueActivity::class.java))
        }

        btnModifier.setOnClickListener {
            startActivity(Intent(this, ModifierCantiqueActivity::class.java))
        }

        btnSupprimer.setOnClickListener {
            startActivity(Intent(this, SupprimerCantiqueActivity::class.java))
        }

        // Champs pour la notification
        val editTitle = findViewById<EditText>(R.id.editNotificationTitle)
        val editBody = findViewById<EditText>(R.id.editNotificationBody)
        val btnEnvoyer = findViewById<Button>(R.id.btnEnvoyerNotification)

        btnEnvoyer.setOnClickListener {
            val title = editTitle.text.toString().trim()
            val body  = editBody.text.toString().trim()

            if (title.isEmpty() || body.isEmpty()) {
                Toast.makeText(this, "Merci de saisir un titre et un message.", Toast.LENGTH_SHORT).show()
            } else {
                sendFcmNotification(title, body)
            }
        }
    }

    private fun sendFcmNotification(title: String, message: String) {
        val json = """
            {
              "to": "/topics/all", 
              "notification": {
                "title": "${title.replace("\"","\\\"")}",
                "body": "${message.replace("\"","\\\"")}"
              },
              "data": {
                "sentBy": "AdminDashboard"
              }
            }
        """.trimIndent()

        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val bodyRequest = RequestBody.create(mediaType, json)

        val request = Request.Builder()
            .url(FCM_API_URL)
            .addHeader("Authorization", "key=$FCM_SERVER_KEY")
            .addHeader("Content-Type", "application/json")
            .post(bodyRequest)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                runOnUiThread {
                    Toast.makeText(this@AdminDashboardActivity, "Échec envoi notification : ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AdminDashboardActivity, "Notification envoyée avec succès !", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@AdminDashboardActivity, "Erreur FCM : ${response.code}", Toast.LENGTH_LONG).show()
                    }
                }
                response.close()
            }
        })
    }
}
