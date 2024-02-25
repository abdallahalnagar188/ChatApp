package com.example.chatapp.database

import androidx.compose.runtime.snapshots.Snapshot
import com.example.chatapp.model.AppUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore

fun addUserToFirestoreDB(
    appUser: AppUser,
    addOnSuccessListener: OnSuccessListener<Void>,
    addOnFailureListener: OnFailureListener
) {
    val db = Firebase.firestore
    val collection = db.collection(AppUser.COLLECTION_NAME)
    collection.document(appUser.id!!)
        .set(appUser)
        .addOnSuccessListener(addOnSuccessListener)
        .addOnFailureListener(addOnFailureListener)
}

fun getUserFromFirestoreDB(
    uid: String,
    onSuccessListener: OnSuccessListener<DocumentSnapshot>,
    onFailureListener: OnFailureListener
) {
    val db = Firebase.firestore
    val collection = db.collection(AppUser.COLLECTION_NAME)
    collection.document(uid)
        .get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)

}