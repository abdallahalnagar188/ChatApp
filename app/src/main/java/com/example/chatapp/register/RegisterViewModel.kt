package com.example.chatapp.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterViewModel : ViewModel() {
    val nameState = mutableStateOf("")
    val nameError = mutableStateOf("")
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val showMessage = mutableStateOf("")
    val auth = Firebase.auth

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

    fun sendAuthDataToFirebase() {
        if (validate()) {
            showLoading.value = true
            registerToAuth()
        }
    }

    fun registerToAuth() {
        auth.createUserWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    //navigate to home
                    showMessage.value = "successful"

                }else{
                    showMessage.value = it.exception?.localizedMessage?:""
                    Log.e("TAG","registerToAuth: ${it.exception?.localizedMessage}")
                }

            }
    }
}