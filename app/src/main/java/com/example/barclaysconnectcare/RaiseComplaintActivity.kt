package com.example.barclaysconnectcare

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class RaiseComplaintActivity : AppCompatActivity() {

    private lateinit var queryEditText: EditText
    private lateinit var uploadDocumentButton: Button
    private lateinit var submitButton: Button

    private var documentUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_raise_complaint)
        // Add this to your onCreate method in your main activity or Application class
        FirebaseApp.initializeApp(this)


        queryEditText = findViewById(R.id.editTextQuery)
        uploadDocumentButton = findViewById(R.id.buttonUploadDocument)
        submitButton = findViewById(R.id.buttonSubmit)

        uploadDocumentButton.setOnClickListener {
            openFileChooser()
        }

        submitButton.setOnClickListener {
            submitComplaint()
            Toast.makeText(this,"Successfully Submitted",Toast.LENGTH_SHORT).show()
        }
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf" // Set the MIME type as required
        startActivityForResult(intent, FILE_REQUEST_CODE)
    }

    private fun submitComplaint() {
        val query = queryEditText.text.toString()
        val email = intent.getStringExtra("EMAIL") // Assuming "USERNAME" is the key for email in the intent extras
        val database = FirebaseDatabase.getInstance().reference
        val key = database.push().key

        if (key != null) {
            val complaintRef = database.child("complaints").child(key)
            complaintRef.child("query").setValue(query)
            complaintRef.child("email").setValue(email) // Save user's email along with the complaint

            if (documentUri != null) {
                // Upload document to Firebase Storage and store its URI in the Realtime Database
                complaintRef.child("documentUri").setValue(documentUri.toString())
            }
        }

        // Reset fields after submission
        queryEditText.text.clear()
        documentUri = null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.data != null) {
            documentUri = data.data
            //
        // Toast.makeText(this,"Successfully Submitted",Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val FILE_REQUEST_CODE = 1
    }
}

//
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import com.amplifyframework.core.Amplify
//import com.amplifyframework.AmplifyException
//import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
//import com.amplifyframework.storage.s3.AWSS3StoragePlugin
//import java.io.File
//
//class RaiseComplaintActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_upload_text)
//
//        // Call the function to upload text
//        uploadText("Hello, World!")
//
//        // Replace documentUri with the actual Uri of the document you want to upload
//        val documentUri: Uri = /* Uri of the document */
//            uploadDocument(documentUri)
//    }
//
//    private fun configureAmplify() {
//        try {
//            Amplify.addPlugin(AWSCognitoAuthPlugin())
//            Amplify.addPlugin(AWSS3StoragePlugin())
//            Amplify.configure(applicationContext)
//
//            Log.i("kilo", "Initialized Amplify")
//        } catch (error: AmplifyException) {
//            Log.e("kilo", "Could not initialize Amplify", error)
//        }
//    }
//
//    private fun uploadText(text: String) {
//        // Generate a unique key for the text using timestamp and a random string
//        val timestamp = System.currentTimeMillis()
//        val randomString = (0..5).map { ('a'..'z').random() }.joinToString("")
//        val textKey = "$timestamp-$randomString.txt"
//
//        // Convert the text to a byte array
//        val textBytes = text.toByteArray()
//
//        // Upload the text to S3
//        Amplify.Storage.uploadData(
//            textKey,
//            textBytes,
//            {
//                Log.i("kilo", "Text uploaded successfully. Key: $textKey")
//            },
//            { error ->
//                Log.e("kilo", "Failed to upload text", error)
//            }
//        )
//    }
//
//    private fun uploadDocument(documentUri: Uri) {
//        val file = File(documentUri.path!!)
//
//        // Generate a unique key for the document using timestamp and a random string
//        val timestamp = System.currentTimeMillis()
//        val randomString = (0..5).map { ('a'..'z').random() }.joinToString("")
//        val documentKey = "$timestamp-$randomString.txt"
//
//        // Upload the document to S3
//        Amplify.Storage.uploadFile(
//            documentKey,
//            file,
//            { result ->
//                Log.i("kilo", "Document uploaded successfully. Key: ${result.key}")
//            },
//            { error ->
//                Log.e("kilo", "Failed to upload document", error)
//            }
//        )
//    }
//}
