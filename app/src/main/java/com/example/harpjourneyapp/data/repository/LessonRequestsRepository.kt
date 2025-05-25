package com.example.harpjourneyapp.data.repository

import android.util.Log
import com.example.harpjourneyapp.data.LessonRequests
import com.example.harpjourneyapp.data.StudentProfile
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId

class LessonRequestRepository(private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()) {

    //Send a lesson request to a tutor
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

    //Tutor gets pending lesson requests
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

    //Tutor accepts lesson, update the status and assign student to tutor
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

    //Get the lesson date, student and tutor
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

    //Get lessons for student
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

    //Get lessons for tutor
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

    //Get student for tutor by id
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
}