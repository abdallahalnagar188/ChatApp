package com.example.chatapp.model

data class Room(
    var roomId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val categoryId: String? = null
){
    companion object{
        const val COLLECTION_NAME = "Room"
    }
}
