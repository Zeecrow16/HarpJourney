package com.example.harpjourneyapp.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.harpjourneyapp.data.TutorProfile
import com.example.harpjourneyapp.presentation.components.student.CustomFindTutorCard
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class FindTutorCardTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val sampleTutor = TutorProfile(
        email = "harp.tutor@example.com",
        bio = "Experienced harp tutor with over 10 years of experience.",
        harpType = "Lever",
        specialisations = listOf("Classical", "Folk"),
        availability = listOf(1716681600000)
    )

    @Test
    fun displaysTutorDetails_correctly() {
        composeRule.setContent {
            CustomFindTutorCard(
                tutor = sampleTutor,
                selectedDate = LocalDate.of(2024, 5, 26),
                onRequestLessonClick = { _, _, _ -> },
                onSelectNewLessonClick = {}
            )
        }

        composeRule.onNodeWithText("harp.tutor@example.com").assertExists()
        composeRule.onNodeWithText("Experienced harp tutor with over 10 years of experience.").assertExists()
        composeRule.onNodeWithText("Harp Type: Lever").assertExists()
        composeRule.onNodeWithText("Specialisations: Classical, Folk").assertExists()
        composeRule.onNodeWithText("Available on:").assertExists()
        composeRule.onNodeWithText("26-05-2024").assertExists()
    }

    @Test
    fun buttonsTriggerCallbacks_correctly() {
        var lessonRequested = false
        var newLessonSelected = false

        composeRule.setContent {
            CustomFindTutorCard(
                tutor = sampleTutor,
                selectedDate = LocalDate.of(2024, 5, 26),
                onRequestLessonClick = { _, _, _ -> lessonRequested = true },
                onSelectNewLessonClick = { newLessonSelected = true }
            )
        }

        composeRule.onNodeWithText("Request a Lesson").performClick()
        composeRule.runOnIdle {
            assert(lessonRequested)
        }

        composeRule.onNodeWithText("Select New Lesson").performClick()
        composeRule.runOnIdle {
            assert(newLessonSelected)
        }
    }
}