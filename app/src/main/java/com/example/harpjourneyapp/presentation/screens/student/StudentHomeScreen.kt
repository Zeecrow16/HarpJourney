package com.example.harpjourneyapp.presentation.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.components.DatePickerModal
import com.example.harpjourneyapp.presentation.components.common.ViewUpcomingLessons
//import com.example.harpjourneyapp.presentation.screens.tutor.TutorHomePageViewModel
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harpjourneyapp.R
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.common.CustomButton
import com.example.harpjourneyapp.ui.theme.PurplePrimary


@Composable
fun StudentHomeScreen(
    navController: NavHostController,
    viewModel: StudentHomePageViewModel = viewModel()
) {
    val userRole = "Student"
    val upcomingLessons by viewModel.upcomingLessons.collectAsState()
    val pageTitle = AppTitles.titles.StudentHome

    LaunchedEffect(Unit) {
        viewModel.loadUpcomingLessons()
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


            Spacer(modifier = Modifier.height(24.dp))

            ViewUpcomingLessons(lessons = upcomingLessons)

            Button(
                onClick = { navController.navigate("Student Edit Profile") },
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
