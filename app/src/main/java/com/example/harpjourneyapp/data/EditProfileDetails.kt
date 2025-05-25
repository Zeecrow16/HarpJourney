package com.example.harpjourneyapp.data

data class EditProfileDetails(
    val firstName: String = "",
    val surname: String = "",
    val location: String = "",
    val phoneNumber: String = "",
    val firstNameError: String? = null,
    val surnameError: String? = null,
    val locationError: String? = null,
    val phoneNumberError: String? = null
)
