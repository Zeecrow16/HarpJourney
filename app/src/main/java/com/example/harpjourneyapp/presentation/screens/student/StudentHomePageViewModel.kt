package com.example.harpjourneyapp.presentation.screens.student


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.LessonRequests
import com.example.harpjourneyapp.data.repository.LessonRequestRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId


class StudentHomePageViewModel(
    private val lessonRequestRepository: LessonRequestRepository = LessonRequestRepository()
) : ViewModel() {

    private val _upcomingLessons = MutableStateFlow<List<LessonRequests>>(emptyList())
    val upcomingLessons: StateFlow<List<LessonRequests>> = _upcomingLessons

    fun loadUpcomingLessons() {
        val studentId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            val lessons = lessonRequestRepository.getUpcomingLessonsForStudent(studentId)
            _upcomingLessons.value = lessons
        }
    }

    fun loadSpecificLesson(tutorId: String, date: LocalDate) {
        val studentId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            val timestamp = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val lessons = lessonRequestRepository.getLessonByStudentTutorDate(studentId, tutorId, timestamp)
            _upcomingLessons.value = lessons
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