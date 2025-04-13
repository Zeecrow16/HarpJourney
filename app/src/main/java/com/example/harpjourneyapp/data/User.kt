package com.example.harpjourneyapp.data

import java.util.UUID

data class User(
    val uid: UUID = UUID.randomUUID(),
    val email: String,
    val role: String
)
