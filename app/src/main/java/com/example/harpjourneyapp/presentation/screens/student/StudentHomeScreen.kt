package com.example.harpjourneyapp.presentation.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.components.common.ViewUpcomingLessons
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harpjourneyapp.AppViewModelProvider
import com.example.harpjourneyapp.R
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.navigation.NavScreen
import com.example.harpjourneyapp.ui.theme.PurplePrimary

@Composable
fun StudentHomeScreen(
    navController: NavHostController,
    viewModel: StudentHomePageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val userRole = "Student"
    val upcomingLessons by viewModel.upcomingLessons.collectAsState()
    val pageTitle = AppTitles.titles.StudentHome

    LaunchedEffect(Unit) {
        viewModel.loadUpcomingLessons()
        viewModel.logoutEvent.collect {
            navController.navigate(NavScreen.Login.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, userRole = userRole)
        },
        containerColor = BeigeBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                item {
                    Text(
                        text = pageTitle,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .testTag("PageTitle")
                    )
                }

                item {
                    ViewUpcomingLessons(
                        lessons = upcomingLessons,
                        onCancelLesson = { lesson -> viewModel.cancelLesson(lesson) },
                        onRescheduleLesson = { lesson, newDateMillis ->
                            viewModel.rescheduleLesson(lesson, newDateMillis)
                        }
                    )
                }

                item {
                    Button(
                        onClick = { navController.navigate("Student Edit Profile") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        contentPadding = PaddingValues(vertical = 6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Update Details",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { viewModel.logout() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.logoutbutton),
                                contentDescription = "Logout"
                            )
                        }
                    }
                }
            }
        }
    }
}
