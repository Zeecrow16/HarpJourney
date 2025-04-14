package com.example.harpjourneyapp.presentation.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.BottomNavBar
import com.example.harpjourneyapp.presentation.components.CustomBio
import com.example.harpjourneyapp.presentation.components.CustomDropdownMenu
import com.example.harpjourneyapp.presentation.components.HarpTypeSelector
import com.example.harpjourneyapp.presentation.components.SpecialisationPicker
import com.example.harpjourneyapp.ui.theme.BeigeBackground

@Composable
fun StudentProfileScreen(navController: NavHostController, viewModel: StudentProfileViewModel = viewModel()) {

    val pageTitle = AppTitles.titles.StudentProfile
    var bio by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.populateDropdowns()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = pageTitle,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

            CustomBio(
                bio = bio,
                onBioChange = { bio = it }
            )

            CustomDropdownMenu(
                label = "Skill Level",
                selectedItem = viewModel.selectedSkillLevel,
                items = viewModel.skillLevels.value,
                onItemSelected = { viewModel.onSkillLevelSelected(it) }
            )
            Spacer(modifier = Modifier.height(10.dp))

            HarpTypeSelector(
                harpTypes = viewModel.harpTypes.value,
                selectedHarp = viewModel.selectedHarpType,
                onHarpSelected = { viewModel.onHarpSelected(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            SpecialisationPicker(
                allOptions = viewModel.tags.value,
                selectedOptions = viewModel.selectedTags,
                onSelectionChange = { viewModel.selectedTags = it }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                   //handle logic
                },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Save", fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        BottomNavBar(
            navController = navController,
            userRole = "Student",
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudentProfileScreenPreview() {
    StudentProfileScreen(navController = rememberNavController())
}



