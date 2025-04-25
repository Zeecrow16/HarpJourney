package com.example.harpjourneyapp.data.repository

import android.util.Log
import com.example.harpjourneyapp.data.StudentProfile
import com.example.harpjourneyapp.enum.SkillLevel
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
        val doc = firestore.collection("users")
            .document(uid)
            .get()
            .await()
        return doc.toObject(StudentProfile::class.java)
    }

    suspend fun getUserSkillLevel(uid: String) {
        val doc = firestore.collection("users")
            .document(uid)
            .get()
            .await()

        val skillLevelString = doc.getString("skill_level")

    }}