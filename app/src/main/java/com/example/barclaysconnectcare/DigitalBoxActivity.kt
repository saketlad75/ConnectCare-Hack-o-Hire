package com.example.barclaysconnectcare

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class DigitalBoxActivity : AppCompatActivity() {
    lateinit var Chatbot: ImageButton
    lateinit var Docupload: ImageButton
    lateinit var RaiseComp: ImageButton
    lateinit var Myticks: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_digital_box)
        Myticks = findViewById(R.id.Mytickets)
        Chatbot = findViewById(R.id.Barchelp)
        Docupload = findViewById(R.id.DocUpload)
        RaiseComp = findViewById(R.id.RaiseComplaint)
        Chatbot.setOnClickListener {
            val intent = Intent(this@DigitalBoxActivity, ChatbotActivity::class.java)
            startActivity((intent))
        }
        Docupload.setOnClickListener {
            val intent = Intent(this@DigitalBoxActivity,UploadActivity::class.java)
            startActivity(intent)
        }
        RaiseComp.setOnClickListener {
            val intent = Intent(this@DigitalBoxActivity,DeptActivity::class.java)
            startActivity(intent)
        }
        Myticks.setOnClickListener {
            val intent = Intent(this@DigitalBoxActivity,TicketActivity::class.java)
            startActivity(intent)
        }
    }
}