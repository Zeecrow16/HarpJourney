package com.example.harpjourneyapp.data

import java.time.LocalDate

data class LessonRequests(
    var id: String = "",
    var studentId: String = "",
    var tutorId: String = "",
    var message: String = "",
    var date: Long = 0L,
    var status: String = "Pending"
)
