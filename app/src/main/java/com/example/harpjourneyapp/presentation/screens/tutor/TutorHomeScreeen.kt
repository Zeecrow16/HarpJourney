package com.example.harpjourneyapp.presentation.screens.tutor

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.presentation.components.BottomNavBar

@Composable
fun TutorHomeScreen(navController: NavHostController) {
    val userRole = "Tutor"

    BottomNavBar(navController = navController, userRole = userRole)


}

