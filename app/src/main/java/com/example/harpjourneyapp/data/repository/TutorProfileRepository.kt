package com.example.harpjourneyapp.data.repository

import com.example.harpjourneyapp.data.StudentProfile
import com.example.harpjourneyapp.data.TutorProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TutorProfileRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    suspend fun saveUserProfile(uuid: String, profile: TutorProfile) {
        firestore.collection("users")
            .document(uuid)
            .set(profile)
            .await()
    }

    suspend fun getUserProfile(uid: String): TutorProfile? {
        val doc = firestore.collection("users")
            .document(uid)
            .get()
            .await()
        return doc.toObject(TutorProfile::class.java)
    }
}