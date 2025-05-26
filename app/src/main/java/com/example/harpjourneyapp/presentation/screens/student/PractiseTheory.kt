package com.example.harpjourneyapp.presentation.screens.practice

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.AppViewModelProvider
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.screens.student.PractiseTheoryViewModel
import com.example.harpjourneyapp.ui.theme.BeigeBackground

@Composable
fun PractiseTheory(
    uid: String,
    navController: NavHostController,
    viewModel: PractiseTheoryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val questions by viewModel.filteredQuestions.collectAsState()
    val selectedAnswers = remember { mutableStateListOf<String>() }
    val pageTitle = AppTitles.titles.PractiseTheory
    val context = LocalContext.current

    LaunchedEffect(uid) {
        viewModel.fetchUserSkillLevel()
        viewModel.fetchAllQuestionsAndSkillLevels()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                Text(
                    text = pageTitle,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                )
            }

            if (questions.isEmpty()) {
                item {
                    Box(
                        Modifier
                            .fillParentMaxSize()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.testTag("loading_indicator")
                        )
                    }
                }
            } else {
                itemsIndexed(questions) { index, question ->
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

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (selectedAnswers.isEmpty()) {
                                Toast.makeText(context, "Please select answers before submitting.", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.submitTestToTutor(selectedAnswers)
                                Toast.makeText(context, "Answers submitted.", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text("Submit Answers")
                    }
                }
            }
        }

        BottomNavBar(
            navController = navController,
            userRole = "Student",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}