package com.example.harpjourneyapp.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.navigation.testing.TestNavHostController
import androidx.compose.ui.platform.LocalContext

import com.example.harpjourneyapp.presentation.screens.student.StudentHomeScreen
import org.junit.Rule
import org.junit.Test


class StudentHomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pageTitle_isDisplayed() {
        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            StudentHomeScreen(navController = navController)
        }
        composeTestRule.onNodeWithTag("PageTitle").assertIsDisplayed()
    }

    @Test
    fun updateDetailsButton_isDisplayedAndClickable() {
        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            StudentHomeScreen(navController = navController)
        }

        val buttonNode = composeTestRule.onNodeWithText("Update Details")
        buttonNode.assertIsDisplayed()
        buttonNode.assertHasClickAction()
    }

    @Test
    fun logoutIconButton_isDisplayedAndClickable() {
        composeTestRule.setContent {
            val navController = TestNavHostController(LocalContext.current)
            StudentHomeScreen(navController = navController)
        }

        composeTestRule.onNodeWithContentDescription("Logout")
            .assertIsDisplayed()
            .assertHasClickAction()
    }

}