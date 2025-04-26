package com.example.harpjourneyapp.data

import java.time.format.DateTimeFormatter

data class TutorProfile(
    val email: String = "",
    val role: String = "",
    val bio: String = "",
    val harpType: String = "",
    val specialisations: List<String> = emptyList(),
    val availability: DateTimeFormatter
)
