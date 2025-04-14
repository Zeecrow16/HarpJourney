package com.example.harpjourneyapp.presentation.components.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginFields(
    modifier: Modifier = Modifier,
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    usernameError: String = "",
    passwordError: String = "",
    usernameHasError: Boolean = false,
    passwordHasError: Boolean = false
) {
    Column(modifier = modifier.padding(16.dp)) {

        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            isError = usernameHasError,
            label = { Text("Email", color = Color.DarkGray) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (usernameHasError) {
            Text(
                text = usernameError,
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordHasError,
            label = { Text("Password", color = Color.DarkGray) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (passwordHasError) {
            Text(
                text = passwordError,
                fontSize = 14.sp,
                color = Color.Red,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
