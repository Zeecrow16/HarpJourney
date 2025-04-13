package com.example.harpjourneyapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.harpjourneyapp.R
import com.example.harpjourneyapp.data.model.AuthViewModel
import com.example.harpjourneyapp.enum.SkillLevel
import com.example.harpjourneyapp.presentation.screens.LoginScreen
import com.example.harpjourneyapp.presentation.screens.SignUp
import com.example.harpjourneyapp.presentation.screens.practice.PractiseTheory
import com.example.harpjourneyapp.presentation.screens.student.FindTutor
import com.example.harpjourneyapp.presentation.screens.student.StudentProfileScreen
import com.example.harpjourneyapp.presentation.screens.tutor.TutorProfile
import kotlin.system.exitProcess

sealed class NavScreen(var icon: Int, var route: String) {
    data object Login : NavScreen(R.drawable.homebutton, "Login")
    data object SignUp : NavScreen(R.drawable.profile, "SignUp")

    //Student
    data object StudentHomeScreen : NavScreen(R.drawable.homebutton, "Home")
    data object StudentProfile : NavScreen(R.drawable.profile, "Student Profile")
    data object FindTutor : NavScreen(R.drawable.findtutor, "FindTutor")
    data object PractiseTheory : NavScreen(R.drawable.practiselesson, "Practise Theory")

    //Tutor
    data object TutorHomeScreen : NavScreen(R.drawable.homebutton, "Home")
    data object TutorProfile : NavScreen(R.drawable.profile, "Tutor Profile")
    data object ViewLessons : NavScreen(R.drawable.lessons, "View Lessons")
    data object MarkTest : NavScreen(R.drawable.testresults, "Mark Test")

    data object Logout : NavScreen(R.drawable.logoutbutton, "Logout")
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Login.route
    ) {
        composable(NavScreen.Login.route) {
            val viewModel: AuthViewModel = viewModel()
            LoginScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(NavScreen.SignUp.route) {
            val viewModel: AuthViewModel = viewModel()
            SignUp(
                navController = navController,
                viewModel = viewModel
            )
        }


        //student
//        composable(NavScreen.StudentHomeScreen.route) {
//            StudentHomeScreen((R.string.home_button),
//
//            )
//        }
        composable(NavScreen.StudentProfile.route){
            StudentProfileScreen(navController = navController)
        }

        composable(NavScreen.FindTutor.route){
            FindTutor()
        }
        composable(NavScreen.PractiseTheory.route){
            PractiseTheory(skillLevel = SkillLevel.BEGINNER)
        }


        //tutor
//        composable(NavScreen.TutorHomeScreen.route){
//            TutorHomeScreen()
//        }

        composable(NavScreen.TutorProfile.route){
            TutorProfile()
        }

//        composable(NavScreen.MarkTest.route){
//            MarkTest()
//        }
//
//        composable(NavScreen.ViewLessons.route){
//            ViewLessons()
//        }


        composable(NavScreen.Logout.route) {
            exitProcess(0)
        }
    }
}
