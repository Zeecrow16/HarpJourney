package com.example.harpjourneyapp.data

data class SubmittedTest(
    val studentId: String = "",
    val answers: List<String> = emptyList(),
    val timestamp: Long = 0L,
    val feedback: String? = null,
    val finalScore: Int? = null,
    val isMarked: Boolean = false
)