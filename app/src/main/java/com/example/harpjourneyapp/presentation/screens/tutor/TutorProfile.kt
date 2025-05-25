package com.example.harpjourneyapp.presentation.screens.tutor

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
        ) {
            Text(
                text = pageTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
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
                        CustomBio(
                            bio = viewModel.bio,
                            onBioChange = { viewModel.bio = it }
                        )

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
                                            Toast.makeText(
                                                context,
                                                "Profile saved!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        onError = { error ->
                                            Toast.makeText(context, error, Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleDark),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Save Profile", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }

        BottomNavBar(
            navController = navController,
            userRole = "Tutor",
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}