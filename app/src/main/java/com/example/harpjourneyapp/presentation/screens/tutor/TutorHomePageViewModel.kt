package com.example.harpjourneyapp.presentation.screens.tutor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.LessonRequests
import com.example.harpjourneyapp.data.StudentProfile
import com.example.harpjourneyapp.data.repository.LessonRequestRepository
import com.example.harpjourneyapp.data.repository.TutorProfileRepository
import com.example.harpjourneyapp.presentation.components.tutor.Student
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId



class TutorHomePageViewModel(
    private val lessonRequestRepository: LessonRequestRepository = LessonRequestRepository(),
) : ViewModel() {

    private val _upcomingRequests = MutableStateFlow<List<LessonRequests>>(emptyList())
    val upcomingRequests: StateFlow<List<LessonRequests>> = _upcomingRequests

    fun loadUpcomingRequests() {
        val tutorId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            val requests = lessonRequestRepository.getUpcomingLessonsForTutor(tutorId)
            _upcomingRequests.value = requests
        }
    }

    fun loadSpecificRequest(studentId: String, date: LocalDate) {
        val tutorId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            val timestamp = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val requests = lessonRequestRepository.getLessonByTutorStudentDate(tutorId, studentId, timestamp)
            _upcomingRequests.value = requests
        }
    }

    private val _myStudents = MutableStateFlow<List<Student>>(emptyList())
    val myStudents: StateFlow<List<Student>> = _myStudents

    fun loadMyStudents() {
        val tutorId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            val studentIds = lessonRequestRepository.getAcceptedStudentIdsForTutor(tutorId)

            val students = studentIds.mapNotNull { studentId ->
                lessonRequestRepository.getStudentProfileById(studentId)
            }.map {
                Student(id = it.studentId, name = "${it.firstName} ${it.surname}")
            }

            _myStudents.value = students
        }
    }

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent

    fun cancelLesson(lesson: LessonRequests) {
        viewModelScope.launch {
            try {
                lessonRequestRepository.deleteLessonRequest(lesson.id)
                loadUpcomingRequests()
                _toastEvent.emit("Lesson removed.")
            } catch (e: Exception) {
                Log.e("TutorHomePageVM", "Failed to delete lesson: ${e.localizedMessage}")
            }
        }
    }

    fun rescheduleLesson(lesson: LessonRequests, newDateMillis: Long) {
        viewModelScope.launch {
            lessonRequestRepository.rescheduleLesson(lesson, newDateMillis)
            loadUpcomingRequests()
        }
    }

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent: SharedFlow<Unit> = _logoutEvent

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        viewModelScope.launch {
            _logoutEvent.emit(Unit)
        }
    }

}
