package com.example.harpjourneyapp.data.service

import com.example.harpjourneyapp.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseUserService : UserService {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override suspend fun registerUser(email: String, password: String, role: String): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()

            val user = User(email = email, role = role)

            firestore.collection("users")
                .document(authResult.user?.uid ?: "")
                .set(user)
                .await()

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}