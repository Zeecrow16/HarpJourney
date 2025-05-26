package com.example.harpjourneyapp.components.profile

import androidx.compose.runtime.*
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.harpjourneyapp.presentation.components.profile.CustomBio
import org.junit.Rule
import org.junit.Test

class CustomBioTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test fun customBio_showsHeaderAndLabel() {
        composeTestRule.setContent {
            CustomBio(bio = "", onBioChange = {})
        }
        composeTestRule.onNodeWithText("Bio").assertExists()
        composeTestRule.onNodeWithText("Your Bio").assertExists()
    }
}