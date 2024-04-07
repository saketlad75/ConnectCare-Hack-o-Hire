package com.example.barclaysconnectcare

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    lateinit var register : Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.editTextEmail)
        password = findViewById(R.id.editTextPassword)
        val loginBtn = findViewById<Button>(R.id.buttonLogin)

        auth = FirebaseAuth.getInstance()

        register = findViewById(R.id.buttonRegsiter)
        register.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()

            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                signInUser(userEmail, userPassword)
            }
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    // Pass user's name as an extra in the Intent
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("USERNAME", user?.email)
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}


//    fun requestPermission(view: View){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
//            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
//            {}else if(shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)){
//
//            }else{
//                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//        else{
//            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w("FirebaseLogs", "Fetching FCM registration token failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new FCM registration token
//                val token = task.result
//                Log.w("FirebaseLogs", "Fetching FCM registration: $token ", task.exception)
//
//                // Log and toast
////                val msg = getString(R.string.msg_token_fmt, token)
////                Log.d(TAG, msg)
////                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            })
//
//        }
//    }
//    // Declare the launcher at the top of your Activity/Fragment:
//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission(),
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//
//            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w("FirebaseLogs", "Fetching FCM registration token failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new FCM registration token
//                val token = task.result
//
//                // Log and toast
////                val msg = getString(R.string.msg_token_fmt, token)
////                Log.d(TAG, msg)
////                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            })
//            // FCM SDK (and your app) can post notifications.
//        } else {
//            // TODO: Inform user that that your app will not show notifications.
//        }
//    }
//
//    private fun askNotificationPermission() {
//        // This is only necessary for API level >= 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
//                // TODO: display an educational UI explaining to the user the features that will be enabled
//                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//                //       If the user selects "No thanks," allow the user to continue without notifications.
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }




