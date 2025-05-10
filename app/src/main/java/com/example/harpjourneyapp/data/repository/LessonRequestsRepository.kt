package com.example.harpjourneyapp.data.repository

import android.util.Log
import com.example.harpjourneyapp.data.LessonRequests
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId

class LessonRequestRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    suspend fun sendLessonRequest(
        studentId: String,
        tutorId: String,
        selectedDate: LocalDate,
        message: String? = null
    ) {
        try {
            val lessonRequest = LessonRequests(
                studentId = studentId,
                tutorId = tutorId,
                date = selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                message = message ?: ""
            )

            val result = firestore.collection("lesson_requests")
                .add(lessonRequest)
                .await()

            Log.d("LessonRequest", "Lesson request sent with ID: ${result.id}")

        } catch (e: Exception) {
            Log.e("LessonRequest", "Failed to send lesson request: ${e.localizedMessage}")
        }
    }
    suspend fun getPendingRequestsForTutor(tutorId: String): List<LessonRequests> {
        return try {
            val snapshot = firestore.collection("lesson_requests")
                .whereEqualTo("tutorId", tutorId)
                .whereEqualTo("status", "Pending")
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(LessonRequests::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("LessonRequestRepo", "Failed to fetch pending requests: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun updateLessonStatusAndAssignUsers(
        requestId: String,
        newStatus: String
    ) {
        val lessonRef = firestore.collection("lesson_requests").document(requestId)

        firestore.runTransaction { transaction ->
            val lessonSnapshot = transaction.get(lessonRef)
            val lesson = lessonSnapshot.toObject(LessonRequests::class.java)
                ?: throw Exception("Lesson not found")

            transaction.update(lessonRef, "status", newStatus)

            if (newStatus == "Accepted") {
                val studentRef = firestore.collection("users").document(lesson.studentId)
                val tutorRef = firestore.collection("users").document(lesson.tutorId)

                transaction.update(studentRef, "tutorId", lesson.tutorId)
                transaction.update(tutorRef, "studentIds", FieldValue.arrayUnion(lesson.studentId))
            }
        }.await()
    }


}