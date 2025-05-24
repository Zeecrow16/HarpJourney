package com.example.harpjourneyapp.presentation.screens.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.screens.Interfaces.EditDetailsInterface
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurpleLight
import com.example.harpjourneyapp.ui.theme.PurplePrimary

@Composable
fun StudentEditProfile(
    navController: NavHostController,
    viewModel: EditDetailsInterface,
    userRole: String
) {
    val uiState by viewModel.uiState.collectAsState()
    val pageTitle = AppTitles.titles.EditProfile

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BeigeBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Content takes all space above navbar
            Column(
                modifier = Modifier
                    .weight(1f)  // Take all available space except BottomNavBar height
                    .padding(horizontal = 24.dp, vertical = 16.dp),
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

                OutlinedTextField(
                    value = uiState.firstName,
                    onValueChange = { viewModel.onFirstNameChange(it) },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = PurpleLight)
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.surname,
                    onValueChange = { viewModel.onSurnameChange(it) },
                    label = { Text("Surname") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = PurpleLight)
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.location,
                    onValueChange = { viewModel.onLocationChange(it) },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = PurpleLight)
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.phoneNumber,
                    onValueChange = { viewModel.onPhoneNumberChange(it) },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = PurpleLight)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { viewModel.saveProfile { navController.popBackStack() } },
                        colors = ButtonDefaults.buttonColors(backgroundColor = PurplePrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text("Save", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text("Cancel", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }

            BottomNavBar(navController = navController, userRole = userRole)
        }
    }
}
