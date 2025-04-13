package com.example.harpjourneyapp.data.service

import com.example.harpjourneyapp.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*

interface UserService {
    suspend fun registerUser(email: String, password: String, role: String): Result<User>
}
