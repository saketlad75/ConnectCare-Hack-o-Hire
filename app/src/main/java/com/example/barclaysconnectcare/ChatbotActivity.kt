package com.example.barclaysconnectcare

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.barclaysconnectcare.R
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2.DetectIntentRequest
import com.google.cloud.dialogflow.v2.DetectIntentResponse
import com.google.cloud.dialogflow.v2.QueryInput
import com.google.cloud.dialogflow.v2.SessionName
import com.google.cloud.dialogflow.v2.SessionsClient
import com.google.cloud.dialogflow.v2.SessionsSettings
import com.google.cloud.dialogflow.v2.TextInput
import com.example.barclaysconnectcare.adapters.ChatAdapter
import com.example.barclaysconnectcare.databinding.ActivityChatbotBinding
import com.example.barclaysconnectcare.databinding.ActivityMainBinding
import com.example.barclaysconnectcare.models.Message


import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import java.util.UUID

class ChatbotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatbotBinding
    private var messageList: ArrayList<Message> = ArrayList()
    private var isRegisteringComplaint = false
    private var complaintData: MutableMap<String, String> = mutableMapOf()

//

    //dialogFlow
    private var sessionsClient: SessionsClient? = null
    private var sessionName: SessionName? = null
    private val uuid = UUID.randomUUID().toString()
    private val TAG = "mainactivity"
    private lateinit var chatAdapter: ChatAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting adapter to RecyclerView
        chatAdapter = ChatAdapter(this, messageList)
        binding.chatView.adapter = chatAdapter

        // OnClick listener to update the list and call Dialogflow
        binding.btnSend.setOnClickListener {
            val message: String = binding.editMessage.text.toString()
            if (message.isNotEmpty()) {
                addMessageToList(message, false)
                sendMessageToBot(message)
//                sendMessageBot(message)
            } else {
                Toast.makeText(this, "Please enter text!", Toast.LENGTH_SHORT).show()
            }
        }

        // Initialize bot configuration
        setUpBot()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addMessageToList(message: String, isReceived: Boolean) {
        messageList.add(Message(message, isReceived))
        binding.editMessage.setText("")
        chatAdapter.notifyDataSetChanged()
        binding.chatView.layoutManager?.scrollToPosition(messageList.size - 1)
    }

    private fun setUpBot() {
        try {
            val stream = this.resources.openRawResource(R.raw.credential)
            val credentials: GoogleCredentials = GoogleCredentials.fromStream(stream)
                .createScoped("https://www.googleapis.com/auth/cloud-platform")
            val projectId: String = (credentials as ServiceAccountCredentials).projectId
            val settingsBuilder: SessionsSettings.Builder = SessionsSettings.newBuilder()
            val sessionsSettings: SessionsSettings = settingsBuilder.setCredentialsProvider(
                FixedCredentialsProvider.create(credentials)
            ).build()
            sessionsClient = SessionsClient.create(sessionsSettings)
            sessionName = SessionName.of(projectId, uuid)
            Log.d(TAG, "projectId : $projectId")
        } catch (e: Exception) {
            Log.d(TAG, "setUpBot: " + e.message)
        }
    }

    private fun sendMessageToBot(message: String) {
        val input = QueryInput.newBuilder()
            .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build()
        GlobalScope.launch {
            sendMessageInBg(input)
        }
    }

    private suspend fun sendMessageInBg(
        queryInput: QueryInput
    ) {
        withContext(Default) {
            try {
                val detectIntentRequest = DetectIntentRequest.newBuilder()
                    .setSession(sessionName.toString())
                    .setQueryInput(queryInput)
                    .build()
                val result = sessionsClient?.detectIntent(detectIntentRequest)
                if (result != null) {
                    runOnUiThread {
                        updateUI(result)
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.d(TAG, "doInBackground: " + e.message)
                e.printStackTrace()
            }
        }
    }

    private fun sendMessageBot(message: String) {
        if (message.trim().equals("Register Complaint", ignoreCase = true)) {
            // If user input is "Register Complaint", set the flag and prompt for the next input
            isRegisteringComplaint = true
//            addMessageToList("Please provide details for the complaint.", false)
        } else if (isRegisteringComplaint) {
            // If currently registering a complaint, store the input as complaint data
            complaintData["complaintDetails"] = message
            // Convert the complaint data to JSON format
//            val jsonData = Gson().toJson(complaintData)
            // Display a toast message indicating that the complaint has been recorded
            Toast.makeText(this, "Complaint recorded:", Toast.LENGTH_SHORT).show()
            // Reset the flag and complaint data
            isRegisteringComplaint = false
            complaintData.clear()
        } else {
            // For other inputs, proceed with the regular message sending process
            val input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build()
            GlobalScope.launch {
                sendMessageInBg(input)
            }
        }
    }


    private fun updateUI(response: DetectIntentResponse) {
        val botReply: String = response.queryResult.fulfillmentText
        if (botReply.isNotEmpty()) {
            addMessageToList(botReply, true)
        } else {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}