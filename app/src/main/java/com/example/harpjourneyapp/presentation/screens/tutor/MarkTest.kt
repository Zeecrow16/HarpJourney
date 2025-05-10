package com.example.harpjourneyapp.presentation.screens.tutor

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.ui.theme.BeigeBackground


@Composable
fun MarkTest(
    viewModel: MarkTestViewModel = viewModel(),
    navController: NavHostController
) {
    val submittedTests by viewModel.submittedTests.collectAsState()

    // Fetch all the submitted tests when the Composable is first launched
    LaunchedEffect(Unit) {
        viewModel.fetchAllSubmittedTests()
    }

    // Local context should be accessed only inside a Composable scope
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Unmarked Test Submissions",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            if (submittedTests.isEmpty()) {
                Text("No unmarked submissions found.")
            } else {
                submittedTests.forEach { test ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Student: ${test.studentId}", fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))

                            // Marks list (using rememberSaveable to persist across recompositions)
                            val marks = rememberSaveable { mutableStateListOf<String>() }

                            // Displaying the answers and marks for each question
                            test.answers.forEachIndexed { index, answer ->
                                if (marks.size <= index) marks.add("") // Initialize marks list

                                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                    Text("Q${index + 1}: $answer")
                                    OutlinedTextField(
                                        value = marks[index],
                                        onValueChange = { marks[index] = it },
                                        label = { Text("Mark (0-10)") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }

                            var feedback by remember { mutableStateOf("") }

                            // Feedback section for the tutor
                            OutlinedTextField(
                                value = feedback,
                                onValueChange = { feedback = it },
                                label = { Text("Feedback") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )

                            // Submit button for marking the test
                            Button(
                                onClick = {
                                    // Make sure to call Toast in the composable context
                                    Toast.makeText(
                                        context,
                                        "Marked test for ${test.studentId}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Submit Mark")
                            }
                        }
                    }
                }
            }
        }

        BottomNavBar(
            navController = navController,
            userRole = "Tutor",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}


//@Composable
//fun MarkTest(
//    viewModel: MarkTestViewModel = viewModel(),
//    navController: NavHostController
//) {
//    val submittedTests by viewModel.submittedTests.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchAllSubmittedTests()
//    }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("Submitted Tests", fontSize = 20.sp, fontWeight = FontWeight.Bold)
//
//        if (submittedTests.isEmpty()) {
//            Text("No submissions found.")
//        } else {
//            submittedTests.forEach { test ->
//                Text("Student: ${test.studentId}")
//                test.answers.forEachIndexed { index, answer ->
//                    Text("Q${index + 1}: $answer")
//                }
//                Spacer(modifier = Modifier.height(12.dp))
//            }
//        }
//    }
//}

