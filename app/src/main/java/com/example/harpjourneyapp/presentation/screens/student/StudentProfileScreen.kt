package com.example.harpjourneyapp.presentation.screens.student

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.components.profile.CustomBio
import com.example.harpjourneyapp.presentation.components.profile.SpecialisationPicker
import com.example.harpjourneyapp.presentation.components.profile.CustomSelectPicker
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurpleDark
import kotlinx.coroutines.launch

@Composable
fun StudentProfileScreen(
    navController: NavHostController,
    viewModel: StudentProfileViewModel = viewModel()
) {
    val pageTitle = AppTitles.titles.StudentProfile
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    val uiState by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = pageTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

            if (uiState is StudentProfileUiState.Loading) {
                CircularProgressIndicator()
            } else if (uiState is StudentProfileUiState.Error) {
                Text(text = (uiState as StudentProfileUiState.Error).message, color = Color.Red)
            } else if (uiState is StudentProfileUiState.Success) {
                CustomBio(
                    bio = viewModel.bio,
                    onBioChange = { viewModel.bio = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Skill Level
                CustomSelectPicker(
                    title = "Skill Level",
                    options = viewModel.skillLevels.value,
                    selectedOption = viewModel.selectedSkillLevel,
                    onOptionSelected = { viewModel.selectedSkillLevel = it }
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Harp Type
                CustomSelectPicker(
                    title = "Harp Type",
                    options = viewModel.harpTypes.value,
                    selectedOption = viewModel.selectedHarpType,
                    onOptionSelected = { viewModel.selectedHarpType = it }
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Specialisation
                SpecialisationPicker(
                    allOptions = viewModel.tags.value,
                    selectedOptions = viewModel.selectedTags,
                    onSelectionChange = { viewModel.selectedTags = it }
                )

                Spacer(modifier = Modifier.height(50.dp))

                // Save Button
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.saveUserProfile(
                                onSuccess = {
                                    Toast.makeText(context, "Profile saved!", Toast.LENGTH_SHORT).show()
                                },
                                onError = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleDark),
                    modifier = Modifier.width(100.dp)
                ) {
                    Text("Save", fontSize = 16.sp, color = Color.White)
                }
            }
        }

        BottomNavBar(
            navController = navController,
            userRole = "Student",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudentProfileScreenPreview() {
    StudentProfileScreen(navController = rememberNavController())
}