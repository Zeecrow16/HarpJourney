package com.example.harpjourneyapp.components

import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import com.example.harpjourneyapp.presentation.components.login.LoginFields
import org.junit.Rule
import org.junit.Test

class LoginFieldsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun usernameField_displaysInitialValue() {
        val initialUser = "email@test.com"

        composeTestRule.setContent {
            LoginFields(
                username = initialUser,
                password = "",
                onUsernameChange = {},
                onPasswordChange = {}
            )
        }

        composeTestRule
            .onNodeWithTag("usernameField", useUnmergedTree = true)
            .assertTextEquals(initialUser)
    }

    @Test
    fun passwordField_displaysMaskedPassword() {
        val initialPass = "secret"
        val mask = "•".repeat(initialPass.length)

        composeTestRule.setContent {
            LoginFields(
                username = "",
                password = initialPass,
                onUsernameChange = {},
                onPasswordChange = {}
            )
        }

        composeTestRule
            .onNodeWithTag("passwordField", useUnmergedTree = true)
            .assertTextEquals(mask)
    }

    @Test
    fun showsErrorMessages_whenErrorsAreSet() {
        val usernameErrorMsg = "Invalid email"
        val passwordErrorMsg = "Password too short"

        composeTestRule.setContent {
            LoginFields(
                username = "",
                password = "",
                onUsernameChange = {},
                onPasswordChange = {},
                usernameHasError = true,
                passwordHasError = true,
                usernameError = usernameErrorMsg,
                passwordError = passwordErrorMsg
            )
        }

        composeTestRule
            .onNodeWithTag("usernameError")
            .assertIsDisplayed()
            .assertTextEquals(usernameErrorMsg)

        composeTestRule
            .onNodeWithTag("passwordError")
            .assertIsDisplayed()
            .assertTextEquals(passwordErrorMsg)
    }
}