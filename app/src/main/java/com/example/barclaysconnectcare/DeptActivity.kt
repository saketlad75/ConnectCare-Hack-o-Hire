package com.example.barclaysconnectcare

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class DeptActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dept)

        // Set text for department cards
        findViewById<CardView>(R.id.cardPayments).apply {
            findViewById<TextView>(R.id.textViewDepartment).text = "Payments"
            findViewById<TextView>(R.id.textViewDescription).text = "Payments related queries"
        }

        findViewById<CardView>(R.id.cardCreditCard).apply {
            findViewById<TextView>(R.id.textViewDepartment).text = "Credit Card"
            findViewById<TextView>(R.id.textViewDescription).text = "Credit card related queries"
        }

        findViewById<CardView>(R.id.cardBankAccount).apply {
            findViewById<TextView>(R.id.textViewDepartment).text = "Bank Account Related"
            findViewById<TextView>(R.id.textViewDescription).text = "Bank account related queries"
        }

        findViewById<CardView>(R.id.cardLoan).apply {
            findViewById<TextView>(R.id.textViewDepartment).text = "Loan Related"
            findViewById<TextView>(R.id.textViewDescription).text = "Loan related queries"
        }

        // Set onClickListener for each card
        findViewById<CardView>(R.id.cardPayments).setOnClickListener {
            navigateToRaiseComplaint()
        }

        findViewById<CardView>(R.id.cardCreditCard).setOnClickListener {
            navigateToRaiseComplaint()
        }

        findViewById<CardView>(R.id.cardBankAccount).setOnClickListener {
            navigateToRaiseComplaint()
        }

        findViewById<CardView>(R.id.cardLoan).setOnClickListener {
            navigateToRaiseComplaint()
        }
    }

    private fun navigateToRaiseComplaint() {
        val intent = Intent(this, RaiseComplaintActivity::class.java)
        startActivity(intent)
    }
}
