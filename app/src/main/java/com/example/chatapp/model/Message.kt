package com.example.chatapp.model

data class Message(
    var id: String? = null,
    var content: String? = null,
    var dateTime: Long? = null,
    var senderName: String? = null,
    var senderId: String? = null,
) {
    companion object {
       const val COLLECTION_NAME = "Message"
    }
}
