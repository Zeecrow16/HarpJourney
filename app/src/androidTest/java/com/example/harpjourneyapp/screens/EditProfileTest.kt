package com.example.harpjourneyapp.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.presentation.screens.edit.StudentEditProfile
import org.junit.Rule
import org.junit.Test

class StudentEditProfileTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testTitleIsDisplayed() {
        composeRule.setContent {
            StudentEditProfile(navController = rememberNavController(), userRole = "student")
        }

        composeRule.onNodeWithText("Edit Profile").assertIsDisplayed()
    }

    @Test
    fun testAllTextFieldsAreDisplayed() {
        composeRule.setContent {
            StudentEditProfile(navController = rememberNavController(), userRole = "student")
        }

        composeRule.onNodeWithText("First Name").assertIsDisplayed()
        composeRule.onNodeWithText("Surname").assertIsDisplayed()
        composeRule.onNodeWithText("Location").assertIsDisplayed()
        composeRule.onNodeWithText("Phone Number").assertIsDisplayed()
    }

    @Test
    fun testSaveAndCancelButtonsAreDisplayed() {
        composeRule.setContent {
            StudentEditProfile(navController = rememberNavController(), userRole = "student")
        }

        composeRule.onNodeWithText("Save").assertIsDisplayed()
        composeRule.onNodeWithText("Cancel").assertIsDisplayed()
    }
}