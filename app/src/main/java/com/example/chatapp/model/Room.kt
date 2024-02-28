package com.example.chatapp.model

data class Room(
    var roomId: String?,
    val name: String?,
    val description: String?,
    val categoryId: String?
){
    companion object{
        const val COLLECTION_NAME = "Room"
    }
}
