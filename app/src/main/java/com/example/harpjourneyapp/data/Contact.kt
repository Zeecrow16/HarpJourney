package com.example.harpjourneyapp.data

import java.util.UUID

class Contact(
    var id: UUID,
    var firstName: String,
    var surname: String,
    var email: String
) {
    override fun toString(): String = "$firstName $surname $email"
    }
