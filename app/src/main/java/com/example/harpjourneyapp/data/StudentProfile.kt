package com.example.harpjourneyapp.data

data class StudentProfile(
    val email: String = "",
    val role: String = "",
    val bio: String = "",
    val skill_level: String = "",
    val harpType: String = "",
    val specialisations: List<String> = emptyList()
)
