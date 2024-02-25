package com.example.chatapp.home

import androidx.lifecycle.ViewModel

class HomeViewModel:ViewModel() {
    var navigator : Navigator? = null
    fun goToAddRoomScreen(){
        navigator?.goToAddRoomScreen()
    }

}