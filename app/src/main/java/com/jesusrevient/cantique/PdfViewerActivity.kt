package com.jesusrevient.cantique

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import java.io.File
import java.net.URL
import java.util.concurrent.Executors

class PdfViewerActivity : AppCompatActivity() {

    private lateinit var pdfView: PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        pdfView = findViewById(R.id.pdfView)
        val pdfUrl = intent.getStringExtra("PDF_URL")

        // Télécharger temporairement le fichier PDF dans le cache
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val url = URL(pdfUrl)
                val input = url.openStream()
                val file = File.createTempFile("temp", ".pdf", cacheDir)
                file.outputStream().use { output -> input.copyTo(output) }

                runOnUiThread {
                    pdfView.fromFile(file)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .load()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
