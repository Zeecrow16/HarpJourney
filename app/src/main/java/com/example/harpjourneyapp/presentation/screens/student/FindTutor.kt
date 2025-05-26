package com.example.harpjourneyapp.presentation.screens.student

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.AppViewModelProvider
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.components.student.CustomFindTutorCard
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurplePrimary
import java.time.LocalDate
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FindTutor(
    navController: NavHostController,
    viewModel: FindTutorViewModel = viewModel(factory = AppViewModelProvider.Factory)

){
    val context = LocalContext.current
    val userRole = "Student"
    val selectedDates by viewModel.selectedDates.collectAsState()
    val tutorsState by viewModel.filteredTutorsState.collectAsState()
    val pageTitle = AppTitles.titles.FindTutor

    var isAvailabilitySelected by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                userRole = userRole,
                modifier = Modifier.fillMaxWidth()
            )
        },
        containerColor = BeigeBackground
    ) { paddingValues ->

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BeigeBackground)
        ) {
            val maxContentWidth = if (maxWidth > 600.dp) 600.dp else maxWidth

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = maxContentWidth)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 24.dp)
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

                item {
                    Button(
                        onClick = {
                            val now = Calendar.getInstance()
                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                                    val updatedDates = selectedDates.toMutableList()
                                    updatedDates.add(selectedDate)
                                    viewModel.selectAvailability(updatedDates)
                                },
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Select Your Availability")
                    }
                }

                item {
                    Button(
                        onClick = {
                            viewModel.matchTutorsToSelectedDate()
                            isAvailabilitySelected = true
                        },
                        enabled = selectedDates.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Find a Tutor")
                    }
                }

                if (isAvailabilitySelected) {
                    if (tutorsState.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No tutors found for selected dates.")
                            }
                        }
                    } else {
                        items(tutorsState) { tutor ->
                            val selectedDate = selectedDates.firstOrNull() ?: return@items

                            CustomFindTutorCard(
                                tutor = tutor,
                                selectedDate = selectedDate,
                                onRequestLessonClick = { selectedTutor, date, message ->
                                    viewModel.requestLesson(selectedTutor, date, message) { success, msg ->
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                        if (success) {
                                            navController.popBackStack()
                                        }
                                    }
                                },
                                onSelectNewLessonClick = {
                                    viewModel.selectNewDate()
                                    isAvailabilitySelected = false
                                }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}