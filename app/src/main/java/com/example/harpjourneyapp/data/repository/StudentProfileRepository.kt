package com.example.harpjourneyapp.data.repository

import com.example.harpjourneyapp.data.StudentProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StudentProfileRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    suspend fun saveUserProfile(profile: StudentProfile) {
        firestore.collection("users")
            .document(profile.email)
            .set(profile)
            .await()
    }

    suspend fun getUserProfile(uid: String): StudentProfile? {
        val doc = firestore.collection("users")
            .document(uid)
            .get()
            .await()
        return doc.toObject(StudentProfile::class.java)
    }
}