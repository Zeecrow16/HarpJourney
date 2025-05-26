package com.example.harpjourneyapp.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.example.harpjourneyapp.presentation.components.login.CustomLoginButton
import org.junit.Rule
import org.junit.Test

class LoginButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun button_displaysText_and_onClickIsCalled() {
        var clicked = false
        val buttonText = "Log In"

        composeTestRule.setContent {
            CustomLoginButton(
                text = buttonText,
                onClick = { clicked = true }
            )
        }

        composeTestRule
            .onNodeWithText(buttonText)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(buttonText)
            .performClick()

        composeTestRule.runOnIdle {
            assert(clicked) { "Expected click handler to be invoked" }
        }
    }
}