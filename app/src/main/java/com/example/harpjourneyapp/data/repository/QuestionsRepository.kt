package com.example.harpjourneyapp.data.repository

import android.util.Log
import com.example.harpjourneyapp.data.HarpQuestions
import com.example.harpjourneyapp.data.SubmittedTest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuestionsRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
) {
    suspend fun getAllQuestions(): List<HarpQuestions> {
        val querySnapshot = firestore.collection("harp_questions")
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { doc ->
            doc.toObject(HarpQuestions::class.java)
        }
    }

    suspend fun submitTestToTutor(uid: String?, selectedAnswers: List<String>) {
        if (uid.isNullOrBlank()) {
            throw IllegalArgumentException("UID must not be null or blank")
        }

        val testSubmission = SubmittedTest(
            studentId = uid,
            answers = selectedAnswers,
            timestamp = System.currentTimeMillis()
        )

        firestore.collection("submitted_tests")
            .document(uid)
            .set(testSubmission)
            .await()
    }

    suspend fun getAllSubmittedTests(): List<SubmittedTest> {
        return try {
            val querySnapshot = firestore.collection("submitted_tests")
                .whereEqualTo("isMarked", false)
                .get()
                .await()

            Log.d("QuestionsRepository", "Found ${querySnapshot.documents.size} submitted tests")

            querySnapshot.documents.mapNotNull { doc ->
                val test = doc.toObject(SubmittedTest::class.java)
                if (test != null) {
                    Log.d("QuestionsRepository", "Fetched test with studentId: ${test.studentId}, answers: ${test.answers}")
                } else {
                    Log.w("QuestionsRepository", "Document ${doc.id} could not be parsed into SubmittedTest")
                }
                test
            }

        } catch (e: Exception) {
            Log.e("QuestionsRepository", "Error fetching submitted tests", e)
            emptyList()
        }
    }

    suspend fun markTest(testId: String, feedback: String, score: Int) {
        val testRef = firestore.collection("submitted_tests").document(testId)
        testRef.update(
            mapOf(
                "isMarked" to true,
                "feedback" to feedback,
                "finalScore" to score
            )
        ).await()
    }


}

