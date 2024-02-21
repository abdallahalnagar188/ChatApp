package com.example.chatapp.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    val nameState = mutableStateOf("")
    val nameError = mutableStateOf("")
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")

    fun validate(): Boolean {
        if (nameState.value.isEmpty() || nameState.value.isBlank()) {
            nameError.value = "First Name Required"
            return false
        } else {
            nameError.value = ""
        }
        if (emailState.value.isEmpty() || emailState.value.isBlank()) {
            emailError.value = "Email Required"
            return false
        } else {
            emailError.value = ""
        }
        if (passwordState.value.isEmpty() || passwordState.value.isBlank()) {
            passwordError.value = "Password Required"
            return false
        } else {
            passwordError.value = ""
        }
        return true
    }

    fun sendAuthDataToFirebase(){
        if (validate()){

        }
    }

}