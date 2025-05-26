package com.example.harpjourneyapp.data.repository

import android.util.Log
import com.example.harpjourneyapp.data.LessonRequests
import com.example.harpjourneyapp.data.StudentProfile
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class LessonRequestRepository {

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

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

    suspend fun getLessonByStudentTutorDate(
        studentId: String,
        tutorId: String,
        dateTimestamp: Long
    ): List<LessonRequests> {
        return try {
            val snapshot = firestore.collection("lesson_requests")
                .whereEqualTo("studentId", studentId)
                .whereEqualTo("tutorId", tutorId)
                .whereEqualTo("date", dateTimestamp)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(LessonRequests::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("LessonRequestRepo", "Failed to fetch lesson by student/tutor/date: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun getUpcomingLessonsForStudent(studentId: String): List<LessonRequests> {
        return try {
            val snapshot = firestore.collection("lesson_requests")
                .whereEqualTo("studentId", studentId)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(LessonRequests::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("LessonRequestRepo", "Failed to fetch upcoming lessons: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun getUpcomingLessonsForTutor(tutorId: String): List<LessonRequests> {
        return try {
            val snapshot = firestore.collection("lesson_requests")
                .whereEqualTo("tutorId", tutorId)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(LessonRequests::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("LessonRequestRepo", "Failed to fetch upcoming lessons for tutor: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun getStudentProfileById(studentId: String): StudentProfile? {
        return try {
            val doc = firestore.collection("users")
                .document(studentId)
                .get()
                .await()

            doc.toObject(StudentProfile::class.java)
        } catch (e: Exception) {
            Log.e("LessonRequestRepo", "Failed to fetch student profile: ${e.localizedMessage}")
            null
        }
    }

    suspend fun getLessonByTutorStudentDate(
        tutorId: String,
        studentId: String,
        dateTimestamp: Long
    ): List<LessonRequests> {
        return try {
            val snapshot = firestore.collection("lesson_requests")
                .whereEqualTo("tutorId", tutorId)
                .whereEqualTo("studentId", studentId)
                .whereEqualTo("date", dateTimestamp)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(LessonRequests::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            Log.e("LessonRequestRepo", "Failed to fetch lesson by tutor/student/date: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun getAcceptedStudentIdsForTutor(tutorId: String): List<String> {
        return try {
            val snapshot = firestore.collection("lesson_requests")
                .whereEqualTo("tutorId", tutorId)
                .whereEqualTo("status", "Accepted")
                .get()
                .await()

            snapshot.documents.mapNotNull { it.getString("studentId") }.distinct()
        } catch (e: Exception) {
            Log.e("LessonRequestRepo", "Failed to get accepted student IDs: ${e.localizedMessage}")
            emptyList()
        }
    }

    suspend fun cancelLesson(lesson: LessonRequests) {
        firestore.collection("lesson_requests")
            .document(lesson.id)
            .update("status", "Cancelled")
            .await()
    }

    suspend fun rescheduleLesson(lesson: LessonRequests, newDateMillis: Long) {
        firestore.collection("lesson_requests")
            .document(lesson.id)
            .update("date", newDateMillis)
            .await()
    }

    suspend fun hasLessonRequestOnDate(
        studentId: String,
        tutorId: String,
        date: LocalDate
    ): Boolean {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val selectedDateStr = date.format(formatter)

        val snapshot = firestore.collection("lesson_requests")
            .whereEqualTo("studentId", studentId)
            .whereEqualTo("tutorId", tutorId)
            .get()
            .await()

        return snapshot.documents.any { doc ->
            val timestampMillis = doc.getLong("date") ?: 0L
            val requestDate = Instant.ofEpochMilli(timestampMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .format(formatter)

            Log.d("LessonCheck", "Existing request date: $requestDate vs Selected date: $selectedDateStr")

            requestDate == selectedDateStr
        }
    }
    suspend fun deleteLessonRequest(requestId: String) {
        try {
            firestore.collection("lesson_requests")
                .document(requestId)
                .delete()
                .await()
            Log.d("LessonRequestRepo", "Deleted lesson request with ID: $requestId")
        } catch (e: Exception) {
            Log.e("LessonRequestRepo", "Failed to delete lesson request: ${e.localizedMessage}")
            throw e
        }
    }
}