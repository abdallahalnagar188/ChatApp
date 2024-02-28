package com.example.chatapp.register

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.addUserToFirestoreDB
import com.example.chatapp.model.AppUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterViewModel : ViewModel() {
    val firstNameState = mutableStateOf("")
    val firstNameError = mutableStateOf("")
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val message = mutableStateOf("")
    private val auth = Firebase.auth

    fun validate(): Boolean {
        if (firstNameState.value.isEmpty() || firstNameState.value.isBlank()) {
            firstNameError.value = "First Name Required"
            return false
        } else {
            firstNameError.value = ""
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

    @SuppressLint("SuspiciousIndentation")
    fun sendAuthDataToFirebase() {
        if (validate())
            showLoading.value = true
        registerToAuth()

    }

    fun registerToAuth() {
        auth.createUserWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener {
                showLoading.value = false
                if (it.isSuccessful) {
                    //navigate to home
                    //Add User to Firestore
                    addUserToFirestore(it.result.user?.uid)

                } else {
                    message.value = it.exception?.localizedMessage ?: ""
                    Log.e("TAG", "registerToAuth: ${it.exception?.localizedMessage}")
                }

            }
    }

    fun addUserToFirestore(uid: String?) {
        val appUser = AppUser(
            id = uid,
            firstName = firstNameState.value,
            emil = emailState.value
        )
        showLoading.value = true
        addUserToFirestoreDB(appUser,
            onSuccessListener = {
                message.value = "successful"
                showLoading.value = false
            }, onFailureListener = {
                showLoading.value = false
                message.value = it.localizedMessage ?: ""
            }
        )
    }
}