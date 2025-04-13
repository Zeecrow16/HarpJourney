package com.example.harpjourneyapp.presentation.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.presentation.components.CustomDropdownMenu
import com.example.harpjourneyapp.presentation.components.CustomTextField
import com.example.harpjourneyapp.ui.theme.BeigeBackground

@Composable
fun StudentProfileScreen(navController: NavHostController, viewModel: StudentProfileViewModel = viewModel()) {
    var firstName by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var firstNameError by remember { mutableStateOf(false) }
    var surnameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.populateDropdowns()
    }

    fun validateFields(): Boolean {
        var isValid = true
        if (firstName.isBlank()) {
            firstNameError = true
            isValid = false
        }
        if (surname.isBlank()) {
            surnameError = true
            isValid = false
        }
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = true
            isValid = false
        }
        if (password.isBlank() || password.length < 6) {
            passwordError = true
            isValid = false
        }
        return isValid
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Update Your Profile"
            )
            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                hintText = "First Name",
                text = firstName,
                onNameChange = { firstName = it },
                errorMessage = "First name cannot be empty.",
                errorPresent = firstNameError
            )

            CustomTextField(
                hintText = "Surname",
                text = surname,
                onNameChange = { surname = it },
                errorMessage = "Surname cannot be empty.",
                errorPresent = surnameError
            )

            CustomTextField(
                hintText = "Email",
                text = email,
                onNameChange = { email = it },
                errorMessage = "Please enter a valid email.",
                errorPresent = emailError
            )

            CustomTextField(
                hintText = "Password",
                text = password,
                onNameChange = { password = it },
                errorMessage = "Password must be at least 6 characters.",
                errorPresent = passwordError
            )

            CustomDropdownMenu(
                label = "Skill Level",
                selectedItem = viewModel.selectedSkillLevel,
                items = viewModel.skillLevels.value,
                onItemSelected = { selectedSkillLevel ->
                    viewModel.onSkillLevelSelected(selectedSkillLevel)
                }
            )

            CustomDropdownMenu(
                label = "Specialisation",
                selectedItem = viewModel.selectedTags.joinToString(", "),
                items = viewModel.tags.value,
                onItemSelected = { selectedTag ->
                    viewModel.onTagSelected(selectedTag)
                }
            )

            Button(
                onClick = {
                    if (validateFields()) {
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentProfileScreenPreview() {
    StudentProfileScreen(navController = rememberNavController())
}

