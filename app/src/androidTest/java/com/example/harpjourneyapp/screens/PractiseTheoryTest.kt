package com.example.harpjourneyapp.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.presentation.screens.practice.PractiseTheory
import org.junit.Rule
import org.junit.Test

class PractiseTheoryScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testTitleIsDisplayed() {
        composeRule.setContent {
            PractiseTheory(uid = "testUid", navController = rememberNavController())
        }

        composeRule.onNodeWithText("Practise Theory").assertIsDisplayed()
    }

    @Test
    fun testLoadingIndicatorShownWhenNoQuestions() {
        composeRule.setContent {
            PractiseTheory(uid = "testUid", navController = rememberNavController())
        }

        composeRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun testSubmitButtonIsDisplayed() {
        composeRule.setContent {
            PractiseTheory(uid = "testUid", navController = rememberNavController())
        }

        composeRule.onNodeWithText("Submit Answers").assertIsDisplayed()
    }
}