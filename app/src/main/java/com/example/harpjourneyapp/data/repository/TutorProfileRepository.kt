package com.example.harpjourneyapp.data.repository

import android.util.Log
import com.example.harpjourneyapp.data.StudentProfile
import com.example.harpjourneyapp.data.TutorProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TutorProfileRepository {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    suspend fun saveUserProfile(uuid: String, profile: TutorProfile) {
        firestore.collection("users")
            .document(uuid)
            .set(profile)
            .await()
    }

    suspend fun getUserProfile(uid: String): TutorProfile? {
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            doc.toObject(TutorProfile::class.java)?.copy(tutorId = doc.id)
        } catch (e: Exception) {
            Log.e("TutorRepo", "Error getting user profile: ${e.localizedMessage}")
            null
        }
    }

    suspend fun getAllTutors(): List<TutorProfile> {
        val querySnapshot = firestore.collection("users")
            .whereEqualTo("role", "Tutor")
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { it.toObject(TutorProfile::class.java) }
    }


    suspend fun getTutorIds(): List<String> {
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("role", "Tutor")
                .get()
                .await()
            snapshot.documents.map { it.id }
        } catch (e: Exception) {
            Log.e("TutorRepo", "Failed to get tutor IDs: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun getTutorsAvailabilityByIds(): Map<String, List<Long>> {
        val ids = getTutorIds()
        val map = mutableMapOf<String, List<Long>>()

        for (id in ids) {
            val profile = getUserProfile(id)
            if (profile?.availability != null) {
                map[id] = profile.availability
            }
        }

        return map
    }

    suspend fun getTutorsByIds(ids: List<String>): List<TutorProfile> {
        return ids.mapNotNull { getUserProfile(it) }
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

    fun getCurrentUserUid(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    suspend fun getStudentsFromTutorProfile(tutorId: String): List<StudentProfile> {
        val tutor = getUserProfile(tutorId) ?: return emptyList()
        return tutor.studentIds.mapNotNull { studentId ->
            val doc = firestore.collection("users").document(studentId).get().await()
            doc.toObject(StudentProfile::class.java)?.copy(studentId = studentId)
        }
    }




}