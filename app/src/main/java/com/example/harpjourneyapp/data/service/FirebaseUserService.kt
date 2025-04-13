package com.example.harpjourneyapp.data.service

import android.util.Log
import com.example.harpjourneyapp.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseUserService : UserService {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun registerUser(email: String, password: String, role: String): Result<User> {
        return try {
            Log.d("FirebaseUserService", "Attempting to register user: $email")

            val authResult = auth.createUserWithEmailAndPassword(email, password).await()

            val user = User(
                email = email,
                role = role
            )

            Log.d("FirebaseUserService", "Auth success. UID: ${authResult.user?.uid}")

            firestore.collection("users")
                .document(authResult.user?.uid ?: "")
                .set(user)
                .await()

            Log.d("FirebaseUserService", "User added to Firestore successfully")

            Result.success(user)
        } catch (e: Exception) {
            Log.e("FirebaseUserService", "Error during registration: ${e.message}", e)
            Result.failure(e)
        }
    }

}
