package com.example.harpjourneyapp.presentation.screens.practice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.enum.SkillLevel
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.screens.student.PractiseTheoryViewModel
import com.example.harpjourneyapp.ui.theme.BeigeBackground

@Composable
fun PractiseTheory(
    skillLevel: SkillLevel,
    viewModel: PractiseTheoryViewModel = viewModel(),
    navController: NavHostController
) {
    val userRole = "Student"

    LaunchedEffect(skillLevel) {
        viewModel.loadQuestions(skillLevel)
    }

    val questions by viewModel.questions.collectAsState()

    val selectedAnswers = remember(questions) {
        mutableStateListOf<String?>().apply { repeat(questions.size) { add(null) } }
    }

    val limitedQuestions = questions.take(3)
    var showResults by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        if (limitedQuestions.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                limitedQuestions.forEachIndexed { index, question ->
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "${index + 1}. ${question.question}",
                            style = MaterialTheme.typography.bodyLarge
                        )

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
                                Text(
                                    text = option.lowercase(),
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        showResults = true
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Submit Answers")
                }

                if (showResults) {
                    AlertDialog(
                        onDismissRequest = { showResults = false },
                        title = { Text("Your Results") },
                        text = {
                            Text("Your answers have been submitted!")
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                showResults = false
                            }) {
                                Text("Submit to Tutor")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showResults = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }

        BottomNavBar(
            navController = navController,
            userRole = userRole,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
