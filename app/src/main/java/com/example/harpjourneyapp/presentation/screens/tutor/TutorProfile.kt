package com.example.harpjourneyapp.presentation.screens.tutor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harpjourneyapp.presentation.components.BottomNavBar
import com.example.harpjourneyapp.presentation.components.CustomDropdownMenu
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurplePrimary

@Composable
fun TutorProfile(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var specialisation by remember { mutableStateOf<String?>(null) }
    var grade by remember { mutableStateOf("") }
    var availability by remember { mutableStateOf("") }

    val specialisationOptions = listOf("Classical", "Celtic", "Jazz", "Contemporary")

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
            Text("Tutor Profile", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            CustomDropdownMenu(
                label = "Specialisation",
                selectedItem = specialisation,
                items = specialisationOptions,
                onItemSelected = { specialisation = it }
            )

            OutlinedTextField(
                value = grade,
                onValueChange = { grade = it },
                label = { Text("Grade (e.g. Grade 8, Diploma)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = availability,
                onValueChange = { availability = it },
                label = { Text("Availability") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                singleLine = false,
                maxLines = 4
            )

            Button(
                onClick = {
                    println("Tutor Profile Saved: $name, $email, $specialisation, $grade, $availability")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
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
