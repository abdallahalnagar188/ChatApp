package com.example.chatapp.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.getRoomsFromFirestoreDB
import com.example.chatapp.model.Room

class HomeViewModel:ViewModel() {
    var navigator: Navigator? = null
    val roomListState = mutableStateOf<List<Room>>(listOf())
    fun navigateToAddRoomScreen() {
        navigator?.navigateToAddRoomScreen()
    }
    fun navigateToChatScreen(room: Room){
        navigator?.navigateToChatScreen(room)
    }

    fun getRoomFromFirestore() {
        getRoomsFromFirestoreDB(onSuccessListener = {
            val list = mutableListOf<Room>()
            it.documents.forEach {
                list.add(it.toObject(Room::class.java) ?: return@forEach)
            }

            roomListState.value = list
        }, onFailureListener = {

        })
    }

}