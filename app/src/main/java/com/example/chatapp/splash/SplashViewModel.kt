package com.example.chatapp.splash

import androidx.lifecycle.ViewModel
import com.example.chatapp.model.DataUtils
import com.example.chatapp.database.getUserFromFirestoreDB
import com.example.chatapp.model.AppUser
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SplashViewModel : ViewModel() {
    var navigator: Navigator? = null
    val auth = Firebase.auth
    fun isCurrentUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun navigate() {
        if (isCurrentUserLoggedIn()) {
            getUserFromFirestoreDB(
                auth.currentUser?.uid!!,
                onSuccessListener = {
                    DataUtils.appUser = it.toObject(AppUser::class.java)
                    DataUtils.firebaseUser = auth.currentUser
                    navigator?.navigateToHome()

                },
                onFailureListener = {
                    navigator?.navigateToLogin()
                })
        } else {
            navigator?.navigateToLogin()
        }
    }
}