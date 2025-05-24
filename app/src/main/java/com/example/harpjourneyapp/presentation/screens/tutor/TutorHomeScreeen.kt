package com.example.harpjourneyapp.presentation.screens.tutor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.R
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.navigation.NavScreen
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.components.common.ViewUpcomingLessons
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurplePrimary

@Composable
fun TutorHomeScreen(
    navController: NavHostController,
    viewModel: TutorHomePageViewModel = viewModel()
) {
    val userRole = "Tutor"
    val upcomingRequests by viewModel.upcomingRequests.collectAsState()
    val pageTitle = AppTitles.titles.TutorHome

    LaunchedEffect(Unit) {
        viewModel.loadUpcomingRequests()
        viewModel.logoutEvent.collect {
            navController.navigate(NavScreen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

            Spacer(modifier = Modifier.height(16.dp))

            ViewUpcomingLessons(lessons = upcomingRequests)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("Tutor Edit Profile") },
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Update Details", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { viewModel.logout() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logoutbutton),
                contentDescription = "Logout"
            )
        }

        BottomNavBar(navController = navController, userRole = userRole)
    }
}

@Preview(showBackground = true)
@Composable
fun TutorHomeScreenPreview() {
    val navController = rememberNavController()
    TutorHomeScreen(navController = navController)
}
