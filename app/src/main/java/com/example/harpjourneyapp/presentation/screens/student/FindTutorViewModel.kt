package com.example.harpjourneyapp.presentation.screens.student

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.TutorProfile
import com.example.harpjourneyapp.data.repository.LessonRequestRepository
import com.example.harpjourneyapp.data.repository.TutorProfileRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class FindTutorViewModel(private val repository: TutorProfileRepository = TutorProfileRepository()) : ViewModel() {
    //TODO: work out which ones are needed...
    private val _selectedDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val selectedDates: StateFlow<List<LocalDate>> = _selectedDates

    private val _filteredTutorsState = MutableStateFlow<List<TutorProfile>>(emptyList())
    val filteredTutorsState: StateFlow<List<TutorProfile>> = _filteredTutorsState

    private val _availabilityMap = MutableStateFlow<Map<String, List<Long>>>(emptyMap())
    val availabilityMap: StateFlow<Map<String, List<Long>>> = _availabilityMap

    private val _selectedDateText = MutableStateFlow<String>("")
    val selectedDateText: StateFlow<String> = _selectedDateText

    private val _matchingTutorsText = MutableStateFlow<List<String>>(emptyList())
    val matchingTutorsText: StateFlow<List<String>> = _matchingTutorsText

    private val _matchingResult = MutableStateFlow<Boolean?>(null)
    val matchingResult: StateFlow<Boolean?> = _matchingResult

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate

    fun selectAvailability(selectedDate: LocalDate) {
        _selectedDate.value = selectedDate
    }

    private val lessonRequestRepository = LessonRequestRepository()

    //Get the date of the student
    fun selectAvailability(selectedDates: List<LocalDate>) {
        _selectedDates.value = selectedDates
        _selectedDateText.value = selectedDates.lastOrNull()?.let {
            it.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        } ?: "No date selected"
    }

    //Get the date of the tutor TODO: remove
    fun loadTutorsAvailability() {
        viewModelScope.launch {
            try {
                val tutorsAvailability = repository.getTutorsAvailabilityByIds()
                _availabilityMap.value = tutorsAvailability

                val tutors = repository.getAllTutors()
                _filteredTutorsState.value = tutors

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun matchTutorsToSelectedDate() {
        viewModelScope.launch {
            try {
                val tutorsAvailability = repository.getTutorsAvailabilityByIds()
                _availabilityMap.value = tutorsAvailability

                val selectedDate = _selectedDates.value.firstOrNull()

                if (selectedDate != null) {
                    val matchedTutorUids = tutorsAvailability.filter { (_, availabilityList) ->
                        availabilityList.any { millis ->
                            java.time.Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate() == selectedDate
                        }
                    }.keys.toList()

                    val matchedTutors = repository.getTutorsByIds(matchedTutorUids)

                    _filteredTutorsState.value = matchedTutors
                    _matchingResult.value = matchedTutors.isNotEmpty()
                } else {
                    _filteredTutorsState.value = emptyList()
                    _matchingResult.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _filteredTutorsState.value = emptyList()
                _matchingResult.value = false
            }
        }
    }


    fun selectNewDate() {
        _selectedDates.value = emptyList()
    }

    fun requestLesson(
        tutor: TutorProfile,
        selectedDate: LocalDate?,
        message: String? = null,
        onResult: (Boolean, String) -> Unit
    ) {
        if (selectedDate == null) {
            onResult(false, "Please select a date.")
            return
        }

        viewModelScope.launch {
            val studentId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val alreadyRequested = lessonRequestRepository.hasLessonRequestOnDate(studentId, tutor.tutorId, selectedDate)

            if (alreadyRequested) {
                onResult(false, "You've already requested a lesson with this tutor on this date.")
            } else {
                lessonRequestRepository.sendLessonRequest(studentId, tutor.tutorId, selectedDate, message)
                onResult(true, "Lesson request sent successfully.")
            }
        }
    }


}
