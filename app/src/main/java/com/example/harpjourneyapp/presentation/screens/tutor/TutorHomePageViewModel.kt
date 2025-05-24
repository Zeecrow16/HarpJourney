package com.example.harpjourneyapp.presentation.screens.tutor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.LessonRequests
import com.example.harpjourneyapp.data.repository.LessonRequestRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId


class TutorHomePageViewModel(
    private val lessonRequestRepository: LessonRequestRepository = LessonRequestRepository()
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

    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent: SharedFlow<Unit> = _logoutEvent

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        viewModelScope.launch {
            _logoutEvent.emit(Unit)
        }
    }
}