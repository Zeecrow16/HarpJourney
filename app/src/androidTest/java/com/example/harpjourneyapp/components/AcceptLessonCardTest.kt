package com.example.harpjourneyapp.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.harpjourneyapp.data.LessonRequests
import com.example.harpjourneyapp.presentation.components.lesson.CustomAcceptLessonCard
import org.junit.Rule
import org.junit.Test
import java.util.*

class CustomAcceptLessonCardTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testButtonsAreDisplayed() {
        val request = LessonRequests(
            message = "Confirm session",
            date = System.currentTimeMillis()
        )

        composeRule.setContent {
            CustomAcceptLessonCard(
                request = request,
                onAccept = {},
                onReject = {}
            )
        }

        composeRule.onNodeWithText("Accept").assertExists()
        composeRule.onNodeWithText("Reject").assertExists()
    }
}