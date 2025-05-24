package com.example.harpjourneyapp.presentation.screens.Interfaces

import com.example.harpjourneyapp.data.EditProfileDetails
import kotlinx.coroutines.flow.StateFlow

interface EditDetailsInterface {
    val uiState: StateFlow<EditProfileDetails>
    fun onFirstNameChange(newName: String)
    fun onSurnameChange(newSurname: String)
    fun onLocationChange(newLocation: String)
    fun onPhoneNumberChange(newNumber: String)
    fun saveProfile(onSuccess: () -> Unit)
}