package com.example.barclaysconnectcare
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify
import com.amplifyframework.AmplifyException
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import java.io.File

class UploadActivity : AppCompatActivity() {

    private lateinit var openGalleryButton: Button
    private lateinit var uploadButton: Button
    private lateinit var pdfUri: Uri

    private val getPdfLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            pdfUri = it
            toggleUploadButtonVisibility(true)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_upload)

        configureAmplify()

        openGalleryButton = findViewById(R.id.openGalleryButton)
        uploadButton = findViewById(R.id.uploadButton)

        openGalleryButton.setOnClickListener {
            getPdfLauncher.launch("application/pdf")
        }

        uploadButton.setOnClickListener {
            Toast.makeText(this,"Documenzzt Uploaded",Toast.LENGTH_LONG).show()
            uploadPdf(pdfUri)

        }
    }

    private fun configureAmplify() {
        try {
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)
        } catch (error: AmplifyException) {
            error.printStackTrace()
        }
    }

    private fun uploadPdf(pdfUri: Uri) {
        val localFile = File(pdfUri.path!!)

        Amplify.Storage.uploadFile(
            "public/" + localFile.name, // Replace "your-folder-path/" with your desired S3 folder path
            localFile,
            {
                Toast.makeText(this, "PDF uploaded successfully", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(this, "Failed to upload PDF: ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun toggleUploadButtonVisibility(isVisible: Boolean) {
        uploadButton.visibility = if (isVisible) Button.VISIBLE else Button.GONE
    }
}

