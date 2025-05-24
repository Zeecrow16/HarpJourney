package com.example.harpjourneyapp.presentation.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harpjourneyapp.data.LessonRequests
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ViewUpcomingLessons(
    lessons: List<LessonRequests>,
    modifier: Modifier = Modifier,
    onViewAllClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(
            text = "Upcoming Lessons",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (lessons.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No upcoming lessons, yet!",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 320.dp)
            ) {
                val lessonsToShow = if (lessons.size > 3) lessons.take(3) else lessons
                items(lessonsToShow) { lesson ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        LessonCard(lesson = lesson)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            if (lessons.size > 3 && onViewAllClick != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onViewAllClick,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "View All")
                }
            }
        }
    }
}

@Composable
fun LessonCard(lesson: LessonRequests) {
    val date = Instant.ofEpochMilli(lesson.date)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Date: ${formatter.format(date)}", style = MaterialTheme.typography.titleMedium)
            if (lesson.message.isNotBlank()) {
                Text("Note: ${lesson.message}", style = MaterialTheme.typography.bodyMedium)
            }
            Text("Status: ${lesson.status}", style = MaterialTheme.typography.bodySmall)
        }
    }
}