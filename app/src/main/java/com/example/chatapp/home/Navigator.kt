package com.example.chatapp.home

import com.example.chatapp.model.Room

interface Navigator {
    fun navigateToAddRoomScreen()
    fun navigateToChatScreen(room: Room)

}