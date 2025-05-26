package com.example.harpjourneyapp.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.presentation.screens.student.FindTutor
import org.junit.Rule
import org.junit.Test

class FindTutorScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testPageTitleIsDisplayed() {
        composeRule.setContent {
            FindTutor(navController = rememberNavController())
        }

        composeRule.onNodeWithText("Find a Tutor").assertIsDisplayed()
        composeRule.onNodeWithText("Select Your Availability").assertIsDisplayed()
    }

    @Test
    fun testFindTutorButtonInitiallyDisabled() {
        composeRule.setContent {
            FindTutor(navController = rememberNavController())
        }

        composeRule.onNodeWithText("Find a Tutor").assertIsNotEnabled()
    }

    @Test
    fun testSelectAvailabilityButtonIsDisplayed() {
        composeRule.setContent {
            FindTutor(navController = rememberNavController())
        }

        composeRule.onNodeWithText("Select Your Availability").assertIsDisplayed()
            .assertHasClickAction()
    }
}