package com.example.harpjourneyapp.components

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.harpjourneyapp.MainActivity
import com.example.harpjourneyapp.presentation.components.profile.CustomSelectPicker
import org.junit.Rule
import org.junit.Test

class CustomSelectPickerTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun placeholder_isDisplayedInitially() {
        composeRule.setContent {
            CustomSelectPicker(
                title = "Number",
                options = listOf("One", "Two", "Three"),
                selectedOption = null,
                onOptionSelected = {}
            )
        }

        composeRule
            .onNodeWithText("Select Number")
            .assertIsDisplayed()
    }


    @Test fun cancelDialog_keepsOldSelection() {
        var chosen by mutableStateOf<String?>(null)
        val initial = "Two"
        val options = listOf("One", initial, "Three")

        composeRule.setContent {
            CustomSelectPicker(
                title = "Number",
                options = options,
                selectedOption = initial,
                onOptionSelected = { chosen = it }
            )
        }

        composeRule.onNodeWithText(initial)
            .assertExists()
            .performClick()

        composeRule.onNodeWithText("One").performClick()
        composeRule.onNodeWithText("Cancel").performClick()

        composeRule.onNodeWithText("OK").assertDoesNotExist()
        composeRule.runOnIdle {
            assert(chosen == null)
        }
    }
}