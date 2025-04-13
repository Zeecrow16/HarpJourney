package com.example.harpjourneyapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harpjourneyapp.data.model.AuthViewModelFactory
import com.example.harpjourneyapp.data.model.AuthenticationViewModel
import com.example.harpjourneyapp.data.repository.UserRepository
import com.example.harpjourneyapp.data.service.FirebaseUserService
import com.example.harpjourneyapp.ui.theme.BeigeBackground

@Composable
fun SignUp(
    navController: NavController,
    viewModel: AuthenticationViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Student") }
    val roles = listOf("Student", "Tutor")

    val userService = FirebaseUserService()
    val userRepository = UserRepository(userService)

    val isRegistered by viewModel.isRegistered.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Create Account", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Column {
            roles.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(selected = role == it, onClick = { role = it })
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(selected = role == it, onClick = { role = it })
                    Text(it)
                }
            }
        }

        Button(onClick = {
            viewModel.signUp(email, password, role)
        }) {
            Text("Sign Up")
        }
        if (isRegistered == true) {
            LaunchedEffect(Unit) {
                viewModel.fetchUserRole { role ->
                    when (role) {
                        "Student" -> navController.navigate("Student Home")
                        "Tutor" -> navController.navigate("Tutor Home")
                    }
                }
            }

    } else if (isRegistered == false) {
            Toast.makeText(LocalContext.current, "Registration failed!", Toast.LENGTH_SHORT).show()
        }
    }
}
