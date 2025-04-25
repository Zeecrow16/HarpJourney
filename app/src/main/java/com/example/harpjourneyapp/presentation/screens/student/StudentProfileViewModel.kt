package com.example.harpjourneyapp.presentation.screens.student

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.harpjourneyapp.data.StudentProfile
import com.example.harpjourneyapp.data.repository.StudentProfileRepository
import com.example.harpjourneyapp.enum.HarpType
import com.example.harpjourneyapp.enum.SkillLevel
import com.example.harpjourneyapp.enum.Specialisation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class StudentProfileViewModel(private val repository: StudentProfileRepository = StudentProfileRepository()) : ViewModel() {

    var selectedSkillLevel by mutableStateOf<String?>(null)
    var selectedTags by mutableStateOf<List<String>>(emptyList())
    var selectedHarpType by mutableStateOf<String?>(null)
    var bio by mutableStateOf("")

    var skillLevels = mutableStateOf<List<String>>(emptyList())
    var tags = mutableStateOf<List<String>>(emptyList())
    var harpTypes = mutableStateOf<List<String>>(emptyList())

    private val _state = MutableStateFlow<StudentProfileUiState>(StudentProfileUiState.Loading)
    val state: StateFlow<StudentProfileUiState> get() = _state

    init {
        populateDropdowns()
    }

    private fun populateDropdowns() {
        skillLevels.value = SkillLevel.entries.map { it.name }
        tags.value = Specialisation.entries.map { it.name }
        harpTypes.value = HarpType.entries.map { it.name }
    }

    fun loadUserProfile() {
        _state.value = StudentProfileUiState.Loading
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val profile = repository.getUserProfile(currentUser.uid)
                    if (profile != null) {
                        bio = profile.bio
                        selectedSkillLevel = profile.skill_level
                        selectedHarpType = profile.harpType
                        selectedTags = profile.specialisations
                        _state.value = StudentProfileUiState.Success(profile)
                    } else {
                        _state.value = StudentProfileUiState.Error("Profile not found")
                    }
                } catch (e: Exception) {
                    _state.value = StudentProfileUiState.Error("Error loading profile: ${e.message}")
                }
            }
        } else {
            _state.value = StudentProfileUiState.Error("No authenticated user")
        }
    }

    suspend fun saveUserProfile(onSuccess: () -> Unit, onError: (String) -> Unit) {
        _state.value = StudentProfileUiState.Loading
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val uuid = currentUser.uid
            val profile = StudentProfile(
                email = currentUser.email ?: "",
                role = "Student",
                bio = bio,
                skill_level = selectedSkillLevel ?: "",
                harpType = selectedHarpType ?: "",
                specialisations = selectedTags
            )

            try {
                repository.saveUserProfile(uuid, profile)
                onSuccess()
                _state.value = StudentProfileUiState.Success(profile)
            } catch (e: Exception) {
                onError("Error saving profile: ${e.message}")
                _state.value = StudentProfileUiState.Error("Error saving profile: ${e.message}")
            }
        } else {
            onError("No authenticated user")
            _state.value = StudentProfileUiState.Error("No authenticated user")
        }
    }
}

sealed class StudentProfileUiState {
    object Loading : StudentProfileUiState()
    data class Success(val profile: StudentProfile) : StudentProfileUiState()
    data class Error(val message: String) : StudentProfileUiState()
}