package com.example.harpjourneyapp.presentation.screens.student

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.AppViewModelProvider
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.components.profile.CustomBio
import com.example.harpjourneyapp.presentation.components.profile.CustomSelectPicker
import com.example.harpjourneyapp.presentation.components.profile.SpecialisationPicker
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurpleDark
import kotlinx.coroutines.launch


@Composable
fun StudentProfileScreen(
    navController: NavHostController,
    viewModel: StudentProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pageTitle = AppTitles.titles.StudentProfile
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, userRole = "Student")
        },
        containerColor = BeigeBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = pageTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    when (uiState) {
                        is StudentProfileUiState.Loading -> {
                            CircularProgressIndicator()
                        }

                        is StudentProfileUiState.Error -> {
                            Text(
                                text = (uiState as StudentProfileUiState.Error).message,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        is StudentProfileUiState.Success -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CustomBio(
                                    bio = viewModel.bio,
                                    onBioChange = { viewModel.bio = it }
                                )

                                CustomSelectPicker(
                                    title = "Skill Level",
                                    options = viewModel.skillLevels.value,
                                    selectedOption = viewModel.selectedSkillLevel,
                                    onOptionSelected = { viewModel.selectedSkillLevel = it }
                                )

                                CustomSelectPicker(
                                    title = "Harp Type",
                                    options = viewModel.harpTypes.value,
                                    selectedOption = viewModel.selectedHarpType,
                                    onOptionSelected = { viewModel.selectedHarpType = it }
                                )

                                SpecialisationPicker(
                                    allOptions = viewModel.tags.value,
                                    selectedOptions = viewModel.selectedTags,
                                    onSelectionChange = { viewModel.selectedTags = it }
                                )

                                Button(onClick = {
                                    coroutineScope.launch {
                                        viewModel.saveUserProfile(
                                            onSuccess = {
                                                Toast
                                                    .makeText(context, "Profile saved!", Toast.LENGTH_SHORT)
                                                    .show()
                                            },
                                            onError = { error ->
                                                Toast
                                                    .makeText(context, error, Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                        )
                                    }
                                },
                                    colors = ButtonDefaults.buttonColors(containerColor = PurpleDark)) {
                                    Text("Save Profile")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}