package com.example.harpjourneyapp.data

data class StudentProfile(
    val studentId: String = "",
    val tutorId: String = "",
    val email: String = "",
    val role: String = "",
    val bio: String = "",
    val skill_level: String = "",
    val harpType: String = "",
    val specialisations: List<String> = emptyList(),
    val firstName: String = "",
    val surname: String = "",
    val location: String = "",
    val phoneNumber: String = ""
)
