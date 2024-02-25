package com.example.chatapp.model

data class AppUser(
    val id: String? = null,
    val firstName: String? = null,
    val emil: String? = null
) {
    companion object {
        const val COLLECTION_NAME = "Users"
    }
}