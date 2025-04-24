package com.example.harpjourneyapp.data

data class HarpQuestions(
    val correct_answer: String = "",
    val options: List<String> = emptyList(),
    val question: String = "",
    val skill_level: String = "",
)
