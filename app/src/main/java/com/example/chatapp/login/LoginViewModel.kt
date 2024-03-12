package com.example.chatapp.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.database.getUserFromFirestoreDB
import com.example.chatapp.model.AppUser
import com.example.chatapp.model.DataUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val message = mutableStateOf("")
    private val auth = Firebase.auth
    var navigator : Navigator? = null

    fun validate(): Boolean {
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

    fun navigateToRegisterActivity(){
        navigator?.openRegisterActivity()

    }
    fun registerToAuth() {
        auth.signInWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener {
                showLoading.value = false
                if (it.isSuccessful) {
                    //navigate to home activity
                    navigator?.openHomeActivity()
                    //Add User to Firestore
                    getUserFromFirestore(it.result.user?.uid)

                } else {
                    message.value = it.exception?.localizedMessage ?: ""
                    Log.e("TAG", "registerToAuth: ${it.exception?.localizedMessage}")
                }

            }
    }

    fun getUserFromFirestore(uid: String?) {
        showLoading.value = true
        getUserFromFirestoreDB(uid!!,
            onSuccessListener = {
                val appUser = it.toObject(AppUser::class.java)
                DataUtils.appUser = appUser
                DataUtils.firebaseUser = auth.currentUser
                message.value = "Successful Login"
                showLoading.value = false
                navigator?.openHomeActivity()
            }, onFailureListener = {
                showLoading.value = false
                message.value = it.localizedMessage ?: ""
            }
        )
    }
}