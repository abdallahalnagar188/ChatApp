package com.example.chatapp.addRoom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.addRoomToFirestoreDB
import com.example.chatapp.model.Category
import com.example.chatapp.model.Room

class AddRoomViewModel : ViewModel() {
    var titleState = mutableStateOf("")
    var titleError = mutableStateOf("")
    var descriptionState = mutableStateOf("")
    var descriptionError = mutableStateOf("")
    var navigator: Navigator? = null
    val categoriesList = Category.getCategoriesList()
    var selectedItem = mutableStateOf(categoriesList.get(0))
    val isExpanded = mutableStateOf(false)
    val showLoading = mutableStateOf(false)
    val message = mutableStateOf("")
    fun validate(): Boolean {
        if (titleState.value.isEmpty() || titleState.value.isBlank()) {
            titleError.value = "Title Required"
            return false
        } else {
            titleError.value = ""
        }
        if (descriptionState.value.isEmpty() || descriptionState.value.isBlank()) {
            descriptionError.value = "Description Required"
            return false
        } else {
            descriptionError.value = ""
        }
        return true
    }

    fun addRoomToFirestore() {
        if (validate()) {
            val room = Room(
                null,
                name = titleState.value,
                description = descriptionState.value,
                categoryId = selectedItem.value.id ?: Category.MOVIES
            )
            showLoading.value = true
            addRoomToFirestoreDB(room, onSuccessListener = {
                showLoading.value = false
                message.value = "Room Added Successful "
            }, onFailureListener = {
                showLoading.value = false
                message.value = "Error : ${it.localizedMessage}"
            })
        }
    }

    fun navigateUp() {
        navigator?.navigateUp()
    }


}