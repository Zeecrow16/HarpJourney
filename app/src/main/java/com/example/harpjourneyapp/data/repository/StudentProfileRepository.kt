package com.example.harpjourneyapp.data.repository

import android.util.Log
import com.example.harpjourneyapp.data.StudentProfile
import com.example.harpjourneyapp.data.TutorProfile
import com.example.harpjourneyapp.enum.SkillLevel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class StudentProfileRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    suspend fun saveUserProfile(uuid: String, profile: StudentProfile) {
        firestore.collection("users")
            .document(uuid)
            .set(profile)
            .await()
    }

    suspend fun getUserProfile(uid: String): StudentProfile? {
        return try {
            val doc = firestore.collection("users")
                .document(uid)
                .get()
                .await()
            doc.toObject(StudentProfile::class.java)
        } catch (e: Exception) {
            Log.e("StudentProfileRepository", "Error fetching profile", e)
            null
        }
    }

    suspend fun getUserSkillLevel(uid: String): SkillLevel {
        val profile = getUserProfile(uid)
            ?: throw IllegalStateException("User profile not found for UID: $uid")

        return SkillLevel.valueOf(profile.skill_level.ifEmpty { "BEGINNER" })
    }

    fun getCurrentUserUid(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    suspend fun getAssignedTutorId(uid: String): String? {
        val profile = getUserProfile(uid)
            ?: throw IllegalStateException("User profile not found for UID: $uid")

        return profile.tutorId
    }

    suspend fun updatePersonalDetails(
        uuid: String,
        firstName: String,
        surname: String
    ) {
        firestore.collection("users")
            .document(uuid)
            .update(
                mapOf(
                    "firstName" to firstName,
                    "surname" to surname
                )
            )
            .await()
    }


}