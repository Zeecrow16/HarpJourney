package com.example.harpjourneyapp.data

data class TutorProfile(
    val tutorId: String = "",
    val studentId: List<String> = listOf(),
    val email: String = "",
    val role: String = "",
    val bio: String = "",
    val harpType: String = "",
    val specialisations: List<String> = emptyList(),
    val availability: List<Long> = emptyList()
)