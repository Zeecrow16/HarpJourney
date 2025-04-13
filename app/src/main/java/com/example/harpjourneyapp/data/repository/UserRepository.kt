package com.example.harpjourneyapp.data.repository


import com.example.harpjourneyapp.data.User
import com.example.harpjourneyapp.data.service.UserService

class UserRepository(private val userService: UserService) {

    suspend fun register(email: String, password: String, role: String): Result<User> {
        return userService.registerUser(email, password, role)
    }

//    suspend fun login(email: String, password: String): Result<String> {
//        return userService.loginUser(email, password)
//    }

    fun logout() {
        userService.logout()
    }
}