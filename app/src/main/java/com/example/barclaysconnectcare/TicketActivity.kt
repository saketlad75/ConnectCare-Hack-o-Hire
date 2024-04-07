package com.example.barclaysconnectcare
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class TicketActivity : AppCompatActivity() {

    private lateinit var listViewQueries: ListView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mytickets)

        listViewQueries = findViewById(R.id.listViewQueries)
        database = FirebaseDatabase.getInstance().reference.child("queries")

        // Send query button click listener
//        findViewById<Button>(R.id.btnSendQuery).setOnClickListener {
//            sendQueryToFirebase("Your query here", "Document data here")
//        }

        // Retrieve and display queries
        retrieveQueriesFromFirebase()
    }

    private fun sendQueryToFirebase(query: String, documentData: String) {
        val queryId = database.push().key
        queryId?.let {
            val queryRef = database.child(it)
            val queryData = HashMap<String, Any>()
            queryData["query"] = query
            queryData["documentData"] = documentData
            queryRef.setValue(queryData)
        }
    }

    private fun retrieveQueriesFromFirebase() {
        val queryList = ArrayList<String>()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, queryList)
        listViewQueries.adapter = adapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                queryList.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val query = postSnapshot.child("query").value.toString()
                    val documentData = postSnapshot.child("documentData").value.toString()
                    queryList.add("Query: $query\nDocument Data: $documentData")
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
            }
        })
    }
}
