package com.example.harpjourneyapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.harpjourneyapp.R
import com.example.harpjourneyapp.data.model.AuthViewModelFactory
import com.example.harpjourneyapp.data.model.AuthenticationViewModel
import com.example.harpjourneyapp.data.repository.UserRepository
import com.example.harpjourneyapp.data.service.FirebaseUserService
import com.example.harpjourneyapp.presentation.screens.LoginScreen
import com.example.harpjourneyapp.presentation.screens.SignUp
import com.example.harpjourneyapp.presentation.screens.practice.PractiseTheory
import com.example.harpjourneyapp.presentation.screens.student.FindTutor
import com.example.harpjourneyapp.presentation.screens.tutor.TutorHomeScreen

import com.example.harpjourneyapp.presentation.screens.student.StudentHomeScreen
import com.example.harpjourneyapp.presentation.screens.student.StudentProfileScreen
import com.example.harpjourneyapp.presentation.screens.tutor.MarkTest
import com.example.harpjourneyapp.presentation.screens.tutor.TutorProfile
import com.example.harpjourneyapp.presentation.screens.tutor.ViewLessons

sealed class NavScreen(var icon: Int, open val route: String) {
    // Common routes
    data object Login : NavScreen(R.drawable.homebutton, "Login")
    data object SignUp : NavScreen(R.drawable.profile, "SignUp")

    // Student Pages
    data object StudentHomeScreen : NavScreen(R.drawable.homebutton, "Student Home")
    data object StudentProfile : NavScreen(R.drawable.profile, "Student Profile")
    data object FindTutor : NavScreen(R.drawable.findtutor, "FindTutor")
    data object PractiseTheory : NavScreen(R.drawable.practiselesson, "Practice Theory")

    // Tutor Pages
    data object TutorHomeScreen : NavScreen(R.drawable.homebutton, "Tutor Home")
    data object TutorProfile : NavScreen(R.drawable.profile, "Tutor Profile")
    data object ViewLessons : NavScreen(R.drawable.lessons, "View Lessons")
    data object MarkTest : NavScreen(R.drawable.testresults, "Mark Test")

    data object Logout : NavScreen(R.drawable.logoutbutton, "Logout")
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    val userService = FirebaseUserService()
    val userRepository = UserRepository(userService)


    NavHost(
        navController = navController,
        startDestination = NavScreen.Login.route
    ) {

        // Sign In/Up
        composable(NavScreen.Login.route) {
            val viewModel: AuthenticationViewModel = viewModel(factory = AuthViewModelFactory(userRepository))
            LoginScreen(viewModel = viewModel, navController = navController)
        }

        composable(NavScreen.SignUp.route) {
            val viewModel: AuthenticationViewModel = viewModel(factory = AuthViewModelFactory(userRepository))
            SignUp(navController = navController, viewModel = viewModel)
        }

        // Home Screens
        composable(NavScreen.StudentHomeScreen.route) {
            StudentHomeScreen(navController = navController)
        }
        composable(NavScreen.TutorHomeScreen.route) {
            TutorHomeScreen(navController = navController)
        }

        // Student Pages
        composable(NavScreen.StudentProfile.route) {
            StudentProfileScreen(navController = navController)
        }
        composable(NavScreen.FindTutor.route) {
            FindTutor(navController = navController)
        }

        composable(NavScreen.PractiseTheory.route) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""

            PractiseTheory(uid = uid, navController = navController)
        }

        // Tutor Pages
        composable(NavScreen.TutorProfile.route) {
            TutorProfile(navController = navController)
        }
        composable(NavScreen.ViewLessons.route) {
            ViewLessons(navController = navController)
        }
        composable(NavScreen.MarkTest.route) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""
            MarkTest(navController = navController)
        }

    }
}
