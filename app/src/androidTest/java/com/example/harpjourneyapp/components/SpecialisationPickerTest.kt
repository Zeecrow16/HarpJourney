package com.example.harpjourneyapp.components

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.harpjourneyapp.presentation.components.profile.SpecialisationPicker
import org.junit.Rule
import org.junit.Test

class SpecialisationPickerTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun button_isDisplayedInitially() {
        composeRule.setContent {
            SpecialisationPicker(
                allOptions = listOf("Jazz", "Classical", "Folk"),
                selectedOptions = emptyList(),
                onSelectionChange = {}
            )
        }

        composeRule
            .onNodeWithText("Choose Specialisations")
            .assertIsDisplayed()
    }

    @Test
    fun preselectedTags_areShownAsChips() {
        val selected = listOf("Jazz", "Folk")

        composeRule.setContent {
            SpecialisationPicker(
                allOptions     = listOf("Jazz", "Classical", "Folk"),
                selectedOptions = selected,
                onSelectionChange = {}
            )
        }

        composeRule.onNodeWithText("Choose Specialisations").assertExists()

        selected.forEach { tag ->
            composeRule.onNodeWithText(tag)
                .assertIsDisplayed()
        }
    }

    @Test
    fun cancelingDialog_doesNotInvokeCallback() {
        var callbackCalledWith: List<String>? = null
        val allOptions = listOf("A", "B", "C")

        composeRule.setContent {
            SpecialisationPicker(
                allOptions      = allOptions,
                selectedOptions = emptyList(),
                onSelectionChange = { callbackCalledWith = it }
            )
        }

        composeRule.onNodeWithText("Choose Specialisations")
            .performClick()

        composeRule.onNodeWithText("B").performClick()

        composeRule.onNodeWithText("Discard").performClick()

        composeRule.onNodeWithText("Select Your Specialisations")
            .assertDoesNotExist()

        composeRule.runOnIdle {
            assert(callbackCalledWith == null)
        }
    }

}
