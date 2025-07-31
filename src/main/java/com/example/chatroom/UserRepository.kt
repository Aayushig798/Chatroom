package com.example.chatroom

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(private val auth: FirebaseAuth,
                                         private val firestore: FirebaseFirestore
) {
    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Boolean> =
        try {
            Log.d("UserRepository", "Starting sign-up with email: $email")

            auth.createUserWithEmailAndPassword(email, password).await()

            Log.d("UserRepository", "User created in Firebase Auth")

            val uid = auth.currentUser?.uid
            Log.d("UserRepository", "Fetched UID: $uid")

            if (uid == null) throw Exception("User UID is null after sign-up")

            val user = User(firstName, lastName, email)
            Log.d("UserRepository", "Creating user object: $user")

            saveUserToFirestore(uid, user)  // if it reaches here, you’ll see the saveUser log
            Log.d("UserRepository", "Completed saveUserToFirestore")

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


    private suspend fun saveUserToFirestore(uid:String,user: User) {
        firestore.collection("users").document(uid).set(user).await()
        Log.d("UserRepository", "Saving user to Firestore: $user with UID: $uid")
    }

    suspend fun getCurrentUser(): Result<User?> = try {
        val firebaseUser = auth.currentUser
        Log.d("UserRepository", "Firebase current user: $firebaseUser")

        if (firebaseUser != null) {
            val email = firebaseUser.email
            Log.d("UserRepository", "Email of current user: $email")
            val snapshot = firestore.collection("users")
                .document(firebaseUser.uid)
                .get()
                .await()
            Log.d("UserRepository", "Snapshot exists: ${snapshot.exists()}")
            Log.d("UserRepository", "Snapshot data: ${snapshot.data}")
            val user = snapshot.toObject(User::class.java)
            Log.d("UserRepository", "Fetched user from Firestore: $user")

            Result.Success(user)
        } else {
            Log.e("UserRepository", "FirebaseAuth.currentUser is null")
            Result.Success(null) // No user is logged in
        }
    } catch (e: Exception) {
        Log.e("UserRepository", "Error in getCurrentUser", e)
        Result.Error(e)
    }

}