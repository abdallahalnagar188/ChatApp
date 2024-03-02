package com.example.chatapp.chat

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.getMessageFromFirestoreDB
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import com.google.firebase.firestore.DocumentChange

class ChatViewModel:ViewModel() {
    var navigator: Navigator? = null
    var room: Room? = null
    val messageListState = mutableStateOf<List<Message>>(listOf())

    fun navigateUp() {
        navigator?.navigateUp()
    }

    fun getMessageFromFirestore() {
        getMessageFromFirestoreDB(roomId = room?.roomId!!, listener = { snapshots, e ->
            if (e != null) {
                Log.e("Tag", "${e.message}")
                return@getMessageFromFirestoreDB
            }
            val mutableList = mutableListOf<Message>()
            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        mutableList.add(dc.document.toObject(Message::class.java))
                    }

                    else -> {}
                }
            }
            messageListState.value = mutableList

        })
    }
}