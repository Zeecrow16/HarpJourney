package com.example.harpjourneyapp.data.repository

import com.example.harpjourneyapp.data.HarpQuestions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuestionsRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    suspend fun getQuestionsBySkillLevel(skillLevel: String): List<HarpQuestions> {
        val querySnapshot = firestore.collection("harp_questions")
            .whereEqualTo("skill_level", skillLevel)
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { doc ->
            doc.toObject(HarpQuestions::class.java)
        }
    }

    suspend fun getAllQuestions(): List<HarpQuestions> {
        val querySnapshot = firestore.collection("harp_questions")
            .get()  // No skill level filter here
            .await()

        return querySnapshot.documents.mapNotNull { doc ->
            doc.toObject(HarpQuestions::class.java)
        }

    }
}
