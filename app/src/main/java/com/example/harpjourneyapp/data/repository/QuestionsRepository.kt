package com.example.harpjourneyapp.data.repository

import com.example.harpjourneyapp.data.HarpQuestions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuestionsRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
){
    suspend fun getAllQuestions(): List<HarpQuestions> {
        val querySnapshot = firestore.collection("harp_questions")
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { doc ->
            doc.toObject(HarpQuestions::class.java)
        }
    }


}
