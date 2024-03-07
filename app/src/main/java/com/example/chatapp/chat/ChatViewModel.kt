package com.example.chatapp.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.addMessageFromFirestoreDB
import com.example.chatapp.database.getMessageFromFirestoreDB
import com.example.chatapp.model.DataUtils
import com.example.chatapp.model.Message
import com.example.chatapp.model.Room
import com.google.firebase.firestore.DocumentChange
import java.util.Date

class ChatViewModel:ViewModel() {
    var navigator: Navigator? = null
    var room: Room? = null
    val messageListState = mutableStateOf<List<Message>>(listOf())
    val messageFieldState = mutableStateOf("")

    fun navigateUp() {
        navigator?.navigateUp()
    }

    fun addMessageToFirestore() {
        if (messageFieldState.value.isEmpty() || messageFieldState.value.isBlank())
            return
        val message = Message(
            content = messageFieldState.value,
            dateTime = Date().time,
            senderId = DataUtils.appUser?.id,
            senderName = DataUtils.appUser?.firstName,
            roomId = room?.roomId
        )
        addMessageFromFirestoreDB(
            message,
            roomId = room?.roomId ?: "",
            onSuccessListener = {
                messageFieldState.value = ""
            },
            onFailureListener = {
                Log.e("Tag", it.localizedMessage)
            })
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