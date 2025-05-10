package com.example.harpjourneyapp.presentation.screens.tutor

import androidx.compose.runtime.getValue
import android.text.format.DateFormat
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.TutorProfile
import com.example.harpjourneyapp.data.repository.TutorProfileRepository
import com.example.harpjourneyapp.enum.HarpType
import com.example.harpjourneyapp.enum.Specialisation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TutorProfileViewModel(
    private val repository: TutorProfileRepository = TutorProfileRepository()
) : ViewModel() {

    var selectedTags by mutableStateOf<List<String>>(emptyList())
    var selectedHarpType by mutableStateOf<String?>(null)
    var selectedDates by mutableStateOf<List<Long>>(emptyList())
    var bio by mutableStateOf("")

    var tags = mutableStateOf<List<String>>(emptyList())
    var harpTypes = mutableStateOf<List<String>>(emptyList())

    private val _state = MutableStateFlow<TutorProfileUiState>(TutorProfileUiState.Loading)
    val state: StateFlow<TutorProfileUiState> get() = _state

    init {
        populateDropdowns()
    }

    private fun populateDropdowns() {
        tags.value = Specialisation.entries.map { it.name }
        harpTypes.value = HarpType.entries.map { it.name }
    }

    fun loadUserProfile() {
        _state.value = TutorProfileUiState.Loading
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            viewModelScope.launch {
                try {
                    val profile = repository.getUserProfile(currentUser.uid)
                    if (profile != null) {
                        bio = profile.bio
                        selectedHarpType = profile.harpType
                        selectedTags = profile.specialisations
                        selectedDates = profile.availability
                        _state.value = TutorProfileUiState.Success(profile)
                    } else {
                        _state.value = TutorProfileUiState.Error("Profile not found")
                    }
                } catch (e: Exception) {
                    _state.value = TutorProfileUiState.Error("Error loading profile: ${e.message}")
                }
            }
        } else {
            _state.value = TutorProfileUiState.Error("No authenticated user")
        }
    }

    suspend fun saveUserProfile(onSuccess: () -> Unit, onError: (String) -> Unit) {
        _state.value = TutorProfileUiState.Loading
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val profile = TutorProfile(
                tutorId = currentUser.uid,
                email = currentUser.email ?: "",
                role = "Tutor",
                bio = bio,
                harpType = selectedHarpType ?: "",
                specialisations = selectedTags,
                availability = selectedDates
            )

            try {
                repository.saveUserProfile(currentUser.uid, profile)
                onSuccess()
                _state.value = TutorProfileUiState.Success(profile)
            } catch (e: Exception) {
                onError("Error saving profile: ${e.message}")
                _state.value = TutorProfileUiState.Error("Error saving profile: ${e.message}")
            }
        } else {
            onError("No authenticated user")
            _state.value = TutorProfileUiState.Error("No authenticated user")
        }
    }

    fun toggleDate(selectedDate: Long) {
        if (selectedDates.contains(selectedDate)) {
            selectedDates = selectedDates.filter { it != selectedDate }
        } else {
            selectedDates = selectedDates + selectedDate
        }
    }

    fun formatMillisToReadableDate(millis: Long): String {
        return DateFormat.format("dd-MM-yyyy", millis).toString()
    }
}

sealed class TutorProfileUiState {
    object Loading : TutorProfileUiState()
    data class Success(val profile: TutorProfile) : TutorProfileUiState()
    data class Error(val message: String) : TutorProfileUiState()
}