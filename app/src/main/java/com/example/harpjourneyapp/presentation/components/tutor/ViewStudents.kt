package com.example.harpjourneyapp.presentation.components.tutor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurplePrimary

data class Student(val id: String, val name: String)

@Composable
fun ViewStudents(students: List<Student>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BeigeBackground)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "My Students",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = PurplePrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            textAlign = TextAlign.Center
        )

        if (students.isEmpty()) {
            Text(
                text = "No students assigned yet.",
                color = PurplePrimary.copy(alpha = 0.6f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            students.forEach { student ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    elevation = 6.dp,
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 24.dp)
                    ) {
                        Text(
                            text = student.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PurplePrimary
                        )
                    }
                }
            }
        }
    }
}
