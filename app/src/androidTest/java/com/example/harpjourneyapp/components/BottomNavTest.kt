package com.example.harpjourneyapp.components

import androidx.compose.ui.test.*
import androidx.navigation.NavController
import com.example.harpjourneyapp.navigation.NavScreen
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import org.junit.Rule
import org.junit.Test
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule


class BottomNavTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun bottomNavBar_showsIcon_withContentDescription() {
        val navController = NavController(composeTestRule.activity)

        composeTestRule.setContent {

            BottomNavBar(
                navController = navController,
                userRole = "Student",
                currentRoute = NavScreen.StudentHomeScreen.route
            )
        }

        composeTestRule.onNodeWithContentDescription(
            NavScreen.StudentHomeScreen.route,
            useUnmergedTree = true
        ).assertExists()
    }
}