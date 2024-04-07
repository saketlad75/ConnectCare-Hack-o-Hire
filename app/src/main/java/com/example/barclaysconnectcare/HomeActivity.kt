package com.example.barclaysconnectcare
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.cloud.dialogflow.v2.Intent.Message.CarouselSelect.Item

class HomeActivity : AppCompatActivity() {
    lateinit var  Dbbutton:ImageButton
    lateinit var mytickets:Item
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        Dbbutton = findViewById(R.id.imageButton_db)
        Dbbutton.setOnClickListener {
            val intent = Intent(this@HomeActivity, DigitalBoxActivity::class.java)
            startActivity(intent)
        }

        // Retrieve user's name from Intent extras
        val email = intent.getStringExtra("EMAIL")

        // Display user's name in a TextView
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        welcomeTextView.text = "Welcome, $email"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.menuTicket -> {
//                // Handle the My Tickets menu item click
//                startActivity(Intent(this, TicketActivity::class.java))
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
