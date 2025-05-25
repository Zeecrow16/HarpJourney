package com.example.harpjourneyapp.presentation.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harpjourneyapp.data.EditProfileDetails
import com.example.harpjourneyapp.data.StudentProfile
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

    private fun validateFirstName(name: String): String? {
        if (name.isBlank()) return "First name cannot be empty"
        return null
    }

    private fun validateSurname(name: String): String? {
        if (name.isBlank()) return "Surname cannot be empty"
        return null
    }

    override fun onFirstNameChange(newName: String) {
        val error = validateFirstName(newName)
        _uiState.value = _uiState.value.copy(
            firstName = newName,
            firstNameError = error
        )
    }

    override fun onSurnameChange(newSurname: String) {
        val error = validateSurname(newSurname)
        _uiState.value = _uiState.value.copy(
            surname = newSurname,
            surnameError = error
        )
    }

    private fun validateLocation(location: String): String? {
        if (location.isBlank()) return "Location cannot be empty"
        if (location.length < 3) return "Location is too short"
        return null
    }

    private fun validatePhoneNumber(phone: String): String? {
        if (phone.length != 10) return "Phone number must be exactly 10 digits"
        if (!phone.all { it.isDigit() }) return "Phone number must contain digits only"
        return null
    }

    override fun onLocationChange(newLocation: String) {
        val error = validateLocation(newLocation)
        _uiState.value = _uiState.value.copy(
            location = newLocation,
            locationError = error
        )
    }

    override fun onPhoneNumberChange(newNumber: String) {
        val error = validatePhoneNumber(newNumber)
        _uiState.value = _uiState.value.copy(
            phoneNumber = newNumber,
            phoneNumberError = error
        )
    }

    override fun saveProfile(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val firstNameError = validateFirstName(_uiState.value.firstName)
            val surnameError = validateSurname(_uiState.value.surname)
            val locationError = validateLocation(_uiState.value.location)
            val phoneError = validatePhoneNumber(_uiState.value.phoneNumber)

            if (firstNameError != null || surnameError != null || locationError != null || phoneError != null) {
                _uiState.value = _uiState.value.copy(
                    firstNameError = firstNameError,
                    surnameError = surnameError,
                    locationError = locationError,
                    phoneNumberError = phoneError
                )
                return@launch
            }

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