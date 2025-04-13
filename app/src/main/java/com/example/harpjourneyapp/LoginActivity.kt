package com.example.harpjourneyapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.navigation.compose.rememberNavController
//import com.example.harpjourneyapp.data.model.AuthViewModel
//import com.example.harpjourneyapp.data.repository.UserRepository
//import com.example.harpjourneyapp.data.service.UserService
//import com.example.harpjourneyapp.presentation.screens.LoginScreen
//
//class LoginActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val authService = UserService()
//        val userRepository = UserRepository(authService)
//        val viewModel = AuthViewModel(userRepository)
//
//        setContent {
//            val navController = rememberNavController()
//
//            LoginScreen(
//                viewModel = viewModel,
//                navController = navController
//
//            )
//        }
//    }
//}