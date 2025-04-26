package com.example.harpjourneyapp.presentation.screens.tutor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.enum.HarpType
import com.example.harpjourneyapp.enum.Specialisation
import com.example.harpjourneyapp.presentation.components.DatePickerModal
import com.example.harpjourneyapp.presentation.components.profile.CustomBio
import com.example.harpjourneyapp.presentation.components.profile.CustomSelectPicker
import com.example.harpjourneyapp.presentation.components.profile.SpecialisationPicker
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurpleDark

@Composable
fun TutorProfile(navController: NavHostController,
                 viewModel: TutorProfileViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CustomBio(
                bio = bio,
                onBioChange = { viewModel.onBioChange(it) }
            )

            CustomSelectPicker(
                title = "Harp Specialisation",
                options = viewModel.harpTypeOptions.map { it.name },
                selectedOption = specialisation?.name,
                onOptionSelected = { selected ->
                    viewModel.onSpecialisationChange(HarpType.valueOf(selected))
                }
            )

            SpecialisationPicker(
                allOptions = viewModel.specialisationOptions.map { it.name },
                selectedOptions = selectedSpecialisations.map { it.name },
                onSelectionChange = { selectedStrings ->
                    viewModel.onSpecialisationsChange(
                        selectedStrings.map { Specialisation.valueOf(it) }
                    )
                }
            )

            Button(
                onClick = { showDatePicker = true }
            ) {
                Text(text = "Pick Available Dates")
            }

            if (showDatePicker) {
                DatePickerModal(
                    onDateSelected = { selectedMillis ->
                        viewModel.addOrRemoveDate(selectedMillis)
                    },
                    onDismiss = { showDatePicker = false }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Selected Availability:", style = MaterialTheme.typography.titleMedium)

            selectedDates.sorted().forEach { millis ->
                Text(
                    text = formatMillisToReadableDate(millis),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Button(
                onClick = {
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PurpleDark)
            ) {
                Text("Save Profile")
            }
        }

        BottomNavBar(
            navController = navController,
            userRole = "Tutor",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, name = "Tutor Profile Preview")
@Composable
fun TutorProfileScreenPreview() {
    val navController = rememberNavController()
    TutorProfile(navController = navController)
}
