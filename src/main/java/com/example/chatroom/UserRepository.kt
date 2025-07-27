package com.example.chatroom

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(private val auth: FirebaseAuth,
                     private val firestore: FirebaseFirestore
) {
    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Boolean> =
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(firstName,lastName,email)
            saveUserToFirestore(user)
            //add user to firestore
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }

    suspend fun login(email: String, password: String): Result<Boolean> =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }


    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }

    suspend fun getCurrentUser(): Result<User?> = try {
        val firebaseUser = auth.currentUser

        if (firebaseUser != null) {
            val snapshot = firestore.collection("users")
                .document(firebaseUser.email ?: "")
                .get()
                .await()

            val user = snapshot.toObject(User::class.java)
            Result.Success(user)
        } else {
            Result.Success(null) // No user is logged in
        }
    } catch (e: Exception) {
        Result.Error(e)
    }

}