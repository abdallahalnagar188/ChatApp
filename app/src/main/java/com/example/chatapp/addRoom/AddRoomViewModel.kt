package com.example.chatapp.addRoom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Category

class AddRoomViewModel : ViewModel() {
    var titleState = mutableStateOf("")
    var titleError = mutableStateOf("")
    var descriptionState = mutableStateOf("")
    var descriptionError = mutableStateOf("")
    var navigator: Navigator? = null
    val categoriesList = Category.getCategoriesList()
    var selectedItem = mutableStateOf(categoriesList.get(0))
    val isExpanded = mutableStateOf(false)
    fun navigateUp() {
        navigator?.navigateUp()
    }


}