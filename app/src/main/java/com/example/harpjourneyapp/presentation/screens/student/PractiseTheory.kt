package com.example.harpjourneyapp.presentation.screens.practice

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.screens.student.PractiseTheoryViewModel
import com.example.harpjourneyapp.ui.theme.BeigeBackground

@Composable
fun PractiseTheory(
    uid: String,
    viewModel: PractiseTheoryViewModel = viewModel(),
    navController: NavHostController
) {
    val questions by viewModel.filteredQuestions.collectAsState()
    val selectedAnswers = remember { mutableStateListOf<String>() }
    val pageTitle = AppTitles.titles.PractiseTheory


    LaunchedEffect(uid) {
        viewModel.fetchUserSkillLevel()
        viewModel.fetchAllQuestionsAndSkillLevels()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        )
        {
            Text(
                text = pageTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

            if (questions.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                questions.forEachIndexed { index, question ->
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "${index + 1}. ${question.question}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        question.options.forEach { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val isChecked = selectedAnswers.contains(option)
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = {
                                        if (it) {
                                            selectedAnswers.add(option)
                                        } else {
                                            selectedAnswers.remove(option)
                                        }
                                    }
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
                        // TODO-Empty logic
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Submit Answers")
                }
            }
        }

        BottomNavBar(
            navController = navController,
            userRole = "Student",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}
