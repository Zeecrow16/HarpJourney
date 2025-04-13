package com.example.harpjourneyapp.data

import com.example.harpjourneyapp.enum.SkillLevel

data class HarpQuestions(
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val level: SkillLevel
)
