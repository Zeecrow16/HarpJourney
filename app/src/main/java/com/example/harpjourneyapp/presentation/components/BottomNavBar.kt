package com.example.harpjourneyapp.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavController
import com.example.harpjourneyapp.R
import com.example.harpjourneyapp.navigation.NavScreen

@Composable
private fun createListOfItems(): List<NavScreen> {
    return listOf(
        //Student
        NavScreen.StudentHomeScreen,
        NavScreen.StudentProfile,
        NavScreen.FindTutor,
        NavScreen.PractiseTheory,

        //Tutor
        NavScreen.TutorHomeScreen,
        NavScreen.TutorProfile,
        NavScreen.ViewLessons,
        NavScreen.MarkTest
    )
}

@Composable
fun BottomNavBar(
    navController: NavController,
    userRole: String,
    modifier: Modifier = Modifier
) {
    val items = when (userRole) {
        "Student" -> listOf(
            NavScreen.StudentHomeScreen,
            NavScreen.StudentProfile,
            NavScreen.FindTutor,
            NavScreen.PractiseTheory
        )
        "Tutor" -> listOf(
            NavScreen.TutorHomeScreen,
            NavScreen.TutorProfile,
            NavScreen.ViewLessons,
            NavScreen.MarkTest
        )
        else -> emptyList()
    }

    NavigationBar(
        modifier = modifier,
        containerColor = colorResource(id = R.color.purple_light),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = item.route
                    )
                },
                label = { Text(text = item.route, fontSize = 12.sp) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationRoute!!) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

