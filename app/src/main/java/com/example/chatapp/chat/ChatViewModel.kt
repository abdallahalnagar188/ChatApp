package com.example.chatapp.chat

import androidx.lifecycle.ViewModel

class ChatViewModel:ViewModel() {
var navigator :Navigator? = null
    fun navigateUp(){
        navigator?.navigateUp()
    }
}