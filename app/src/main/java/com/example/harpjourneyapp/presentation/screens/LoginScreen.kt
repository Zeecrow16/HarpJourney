package com.example.harpjourneyapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.R
//import com.example.harpjourneyapp.data.model.AuthState
import com.example.harpjourneyapp.data.model.AuthenticationViewModel
import com.example.harpjourneyapp.navigation.NavScreen
import com.example.harpjourneyapp.presentation.components.login.CustomLoginButton
import com.example.harpjourneyapp.presentation.components.login.LoginFields
import com.example.harpjourneyapp.ui.theme.BeigeBackground

@Composable
fun LoginScreen(
    viewModel: AuthenticationViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

//    val authState by viewModel.authState.collectAsState()
//
//    // Navigate when login is successful
//    LaunchedEffect(authState) {
//        if (authState is AuthState.Success) {
//            navController.navigate("home") {
//                popUpTo("login") { inclusive = true }
//            }
//        }
//    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BeigeBackground)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = R.drawable.harplog),
            contentDescription = "Harp Journey Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Harp Journey",
            fontSize = 32.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoginFields(
            username = email,
            password = password,
            onUsernameChange = { email = it },
            onPasswordChange = { password = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

//        CustomLoginButton(
//            text = "Login",
//            onClick = {
//                viewModel.login(email, password)
//            },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//
//        when (authState) {
//            is AuthState.Loading -> {
//                Spacer(modifier = Modifier.height(16.dp))
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//            }
//            is AuthState.Error -> {
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = (authState as AuthState.Error).message,
//                    color = Color.Red,
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//            }
//            else -> {}
//        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(NavScreen.SignUp.route)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Don't have an account? Sign Up", color = Color.White)
        }
    }
}
