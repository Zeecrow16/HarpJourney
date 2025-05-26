package com.example.harpjourneyapp.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import com.example.harpjourneyapp.presentation.components.tutor.Student
import com.example.harpjourneyapp.presentation.components.tutor.ViewStudents
import org.junit.Rule
import org.junit.Test

class ViewStudentsTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun testDisplaysNoStudentsMessage() {
        composeRule.setContent {
            ViewStudents(students = emptyList())
        }

        composeRule.onNodeWithText("My Students").assertIsDisplayed()
        composeRule.onNodeWithText("No students assigned yet.").assertIsDisplayed()
    }

    @Test
    fun testDisplaysStudentNames() {
        val testStudents = listOf(
            Student(id = "1", name = "Alice"),
            Student(id = "2", name = "Bob")
        )

        composeRule.setContent {
            ViewStudents(students = testStudents)
        }

        composeRule.onNodeWithText("My Students").assertIsDisplayed()
        composeRule.onNodeWithText("Alice").assertIsDisplayed()
        composeRule.onNodeWithText("Bob").assertIsDisplayed()
    }
}