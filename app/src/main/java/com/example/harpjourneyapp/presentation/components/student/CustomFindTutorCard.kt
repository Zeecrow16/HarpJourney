package com.example.harpjourneyapp.presentation.components.student

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.harpjourneyapp.data.TutorProfile
import com.example.harpjourneyapp.ui.theme.PurplePrimary
import java.time.LocalDate
import java.util.*

@Composable
fun CustomFindTutorCard(
    tutor: TutorProfile,
    selectedDate: LocalDate,
    onRequestLessonClick: (TutorProfile, LocalDate, String?) -> Unit,
    onSelectNewLessonClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .background(Color.White, RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.Start
        ) {
            // Display Tutor's Email
            Text(
                text = tutor.email,
                style = MaterialTheme.typography.h6.copy(
                    color = Color(0xFF8A2BE2),  // Purple color
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display Tutor's Bio
            Text(
                text = tutor.bio,
                style = MaterialTheme.typography.body2.copy(
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display Tutor's Harp Type and Specialisations
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Harp Type: ${tutor.harpType}",
                    style = MaterialTheme.typography.body2.copy(
                        color = Color(0xFF6A1B9A),
                        fontWeight = FontWeight.SemiBold
                    ),
                )

                Text(
                    text = "Specialisations: ${tutor.specialisations.joinToString(", ")}",
                    style = MaterialTheme.typography.body2.copy(
                        color = Color(0xFF6A1B9A),
                        fontWeight = FontWeight.SemiBold
                    ),
                )
            }

            // Show Tutor's Availability
            if (tutor.availability.isNotEmpty()) {
                Text(
                    text = "Available on:",
                    style = MaterialTheme.typography.body1.copy(
                        color = Color(0xFF6A1B9A),
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )

                // Format and display each available date
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    tutor.availability.forEach { timestamp ->
                        val date = Date(timestamp)
                        val formattedDate = DateFormat.format("dd-MM-yyyy", date).toString()
                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.body2.copy(
                                color = Color(0xFF5A2A58),
                                fontWeight = FontWeight.Normal
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }

            // Request a Lesson Button
            Button(
                onClick = {
                    val message = "Looking forward to the lesson!"
                    onRequestLessonClick(tutor, selectedDate, message)
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
            ) {
                Text(text = "Request a Lesson")
            }

            // Select New Lesson Button
            Button(
                onClick = onSelectNewLessonClick,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(text = "Select New Lesson")
            }
        }
    }
}
