package com.example.harpjourneyapp.presentation.screens.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harpjourneyapp.enum.SkillLevel
import com.example.harpjourneyapp.presentation.screens.student.PractiseTheoryViewModel
import com.example.harpjourneyapp.ui.theme.BeigeBackground

@Composable
fun PractiseTheory(
    skillLevel: SkillLevel,
    viewModel: PractiseTheoryViewModel = viewModel()
) {
    LaunchedEffect(skillLevel) {
        viewModel.loadQuestions(skillLevel)
    }

    val questions by viewModel.questions
    val selectedAnswers = remember(questions) {
        mutableStateListOf<String?>().apply { repeat(questions.size) { add(null) } }
    }
    var showResults by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    if (questions.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BeigeBackground)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            questions.forEachIndexed { index, question ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "${index + 1}. ${question.question}", style = MaterialTheme.typography.bodyLarge)

                    question.options.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .toggleable(
                                    value = selectedAnswers[index] == option,
                                    onValueChange = {
                                        selectedAnswers[index] = option
                                    }
                                )
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedAnswers[index] == option,
                                onCheckedChange = { selectedAnswers[index] = option }
                            )
                            Text(text = option.lowercase(), modifier = Modifier.padding(start = 8.dp))
                        }
                    }

                    if (showResults) {
                        val correct = question.correctAnswer == selectedAnswers[index]
                        Text(
                            text = if (correct) " Correct" else " Correct Answer: ${question.correctAnswer}",
                            color = if (correct) Color.Green else Color.Red,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    showResults = true
                    showDialog = true
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Submit Answers")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Your Results") },
                    text = {
                        val score = questions.withIndex().count { (i, q) -> q.correctAnswer == selectedAnswers[i] }
                        Text("You got $score out of ${questions.size} correct.")
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDialog = false
                        }) {
                            Text("Submit to Tutor")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PractiseTheoryScreenPreview() {
    PractiseTheory(skillLevel = SkillLevel.BEGINNER)
}