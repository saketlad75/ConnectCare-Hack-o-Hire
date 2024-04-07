package com.example.barclaysconnectcare
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.widget.Button
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.example.barclaysconnectcare.databinding.ActivityDocumentUploadBinding
//
//class DocumentUploadActivity : AppCompatActivity() {
//
//    private lateinit var takePictureLauncher: ActivityDocumentUploadBinding<Intent>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val btnTakePhoto: Button = findViewById(R.id.btnTakePhoto)
//
//        // Initialize the ActivityResultLauncher to take a picture
//        takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//                val imageUri = result.data?.data
//                // Do something with the captured image URI
//                Toast.makeText(this, "Image captured!", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Failed to capture image!", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        btnTakePhoto.setOnClickListener {
//            if (checkCameraPermission()) {
//                openCamera()
//            } else {
//                requestCameraPermission()
//            }
//        }
//    }
//
//    private fun checkCameraPermission(): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.CAMERA
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun requestCameraPermission() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.CAMERA),
//            CAMERA_PERMISSION_REQUEST_CODE
//        )
//    }
//
//    private fun openCamera() {
//        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        takePictureIntent.resolveActivity(packageManager)?.let {
//            // Start the camera activity
//            takePictureLauncher.launch(takePictureIntent)
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                openCamera()
//            } else {
//                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    companion object {
//        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
//    }
//}
