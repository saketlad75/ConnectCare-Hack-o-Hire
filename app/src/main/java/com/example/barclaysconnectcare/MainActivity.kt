package com.example.barclaysconnectcare

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Binder
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts.Intents
import android.view.inputmethod.InputBinding
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnChatbot: FloatingActionButton = findViewById(R.id.btn_chatbot)

        btnChatbot.setOnClickListener {
            val intent = Intent(this@MainActivity, ChatbotActivity::class.java)
            startActivity(intent)
        }
    }


}





