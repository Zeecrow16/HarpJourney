package com.example.harpjourneyapp.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.example.harpjourneyapp.data.LessonRequests
import com.example.harpjourneyapp.presentation.components.common.ViewUpcomingLessons
import org.junit.Rule
import org.junit.Test

class ViewLessonsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val emptyLessons = emptyList<LessonRequests>()

    private val sampleLesson = LessonRequests(
        id = "1",
        date = System.currentTimeMillis(),
        message = "Sample note",
        status = "Scheduled"
    )
    private val lessonsList = listOf(sampleLesson)

    @Test
    fun showsNoLessonsMessage_whenLessonsListIsEmpty() {
        composeTestRule.setContent {
            ViewUpcomingLessons(
                lessons = emptyLessons,
                onCancelLesson = {},
                onRescheduleLesson = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithText("No upcoming lessons, yet!").assertIsDisplayed()
    }

    @Test
    fun showsLessonCard_whenLessonsListIsNotEmpty() {
        composeTestRule.setContent {
            ViewUpcomingLessons(
                lessons = lessonsList,
                onCancelLesson = {},
                onRescheduleLesson = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithTag("LessonCard").assertIsDisplayed()
    }
}