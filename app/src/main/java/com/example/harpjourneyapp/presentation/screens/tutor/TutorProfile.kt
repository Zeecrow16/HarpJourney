package com.example.harpjourneyapp.presentation.screens.tutor

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.DatePickerModal
import com.example.harpjourneyapp.presentation.components.profile.CustomBio
import com.example.harpjourneyapp.presentation.components.profile.CustomSelectPicker
import com.example.harpjourneyapp.presentation.components.profile.SpecialisationPicker
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurpleDark
import kotlinx.coroutines.launch

@Composable
fun TutorProfile(
    navController: NavHostController,
    viewModel: TutorProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var showDatePicker by remember { mutableStateOf(false) }
    val pageTitle = AppTitles.titles.TutorProfile

    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        // Title Text at the top
        Text(
            text = pageTitle,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        )

        // Main content below the title
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                is TutorProfileUiState.Loading -> CircularProgressIndicator()

                is TutorProfileUiState.Error -> {
                    Text(
                        text = (uiState as TutorProfileUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is TutorProfileUiState.Success -> {
                    // bio
                    CustomBio(
                        bio = viewModel.bio,
                        onBioChange = { viewModel.bio = it }
                    )

                    // harp specialisation
                    CustomSelectPicker(
                        title = "Harp Specialisation",
                        options = viewModel.harpTypes.value,
                        selectedOption = viewModel.selectedHarpType,
                        onOptionSelected = { viewModel.selectedHarpType = it }
                    )

                    SpecialisationPicker(
                        allOptions = viewModel.tags.value,
                        selectedOptions = viewModel.selectedTags,
                        onSelectionChange = { viewModel.selectedTags = it }
                    )

                    // Date Picker button
                    Button(onClick = { showDatePicker = true }) {
                        Text("Pick Available Dates")
                    }

                    if (showDatePicker) {
                        DatePickerModal(
                            onDateSelected = { viewModel.toggleDate(it) },
                            onDismiss = { showDatePicker = false }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Available Dates:")
                    viewModel.selectedDates.sorted().forEach { millis ->
                        Text(viewModel.formatMillisToReadableDate(millis))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.saveUserProfile(
                                    onSuccess = {
                                        launch {
                                            Toast.makeText(context, "Profile saved!", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    onError = { error ->
                                        launch {
                                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleDark)
                    ) {
                        Text("Save Profile")
                    }
                }
            }
        }

        BottomNavBar(navController = navController, userRole = "Tutor")
    }
}

//@Preview(showBackground = true, name = "Tutor Profile Preview")
//@Composable
//fun TutorProfileScreenPreview() {
//    val navController = rememberNavController()
//    TutorProfile(navController = navController)
//}
