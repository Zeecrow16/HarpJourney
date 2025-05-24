package com.example.harpjourneyapp.presentation.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.EditProfileDetails
import com.example.harpjourneyapp.data.TutorProfile
import com.example.harpjourneyapp.data.repository.TutorProfileRepository
import com.example.harpjourneyapp.presentation.screens.Interfaces.EditDetailsInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TutorEditDetailsViewModel(
    private val repo: TutorProfileRepository = TutorProfileRepository()
) : ViewModel(), EditDetailsInterface {

    private val _uiState = MutableStateFlow(EditProfileDetails())
    override val uiState: StateFlow<EditProfileDetails> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val uid = repo.getCurrentUserUid() ?: return@launch
            val p = repo.getUserProfile(uid) ?: return@launch
            _uiState.value = EditProfileDetails(
                firstName = p.firstName,
                surname = p.surname,
                location = p.location,
                phoneNumber = p.phoneNumber
            )
        }
    }

    override fun onFirstNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(firstName = newName)
    }

    override fun onSurnameChange(newSurname: String) {
        _uiState.value = _uiState.value.copy(surname = newSurname)
    }

    override fun onLocationChange(newLocation: String) {
        _uiState.value = _uiState.value.copy(location = newLocation)
    }

    override fun onPhoneNumberChange(newNumber: String) {
        _uiState.value = _uiState.value.copy(phoneNumber = newNumber)
    }

    override fun saveProfile(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val uid = repo.getCurrentUserUid() ?: return@launch
            val existing = repo.getUserProfile(uid) ?: TutorProfile(tutorId = uid)
            val updated = existing.copy(
                firstName = _uiState.value.firstName,
                surname = _uiState.value.surname,
                location = _uiState.value.location,
                phoneNumber = _uiState.value.phoneNumber
            )
            repo.saveUserProfile(uid, updated)
            onSuccess()
        }
    }
}