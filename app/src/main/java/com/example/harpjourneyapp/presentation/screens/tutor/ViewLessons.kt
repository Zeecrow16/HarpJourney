package com.example.harpjourneyapp.presentation.screens.tutor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.harpjourneyapp.data.repository.LessonRequestRepository
import com.example.harpjourneyapp.data.titles.AppTitles
import com.example.harpjourneyapp.presentation.components.common.BottomNavBar
import com.example.harpjourneyapp.presentation.components.lesson.CustomAcceptLessonCard
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurpleDark


@Composable
fun ViewLessons(
    navController: NavHostController,
    viewModelInstance: ViewLessonViewModel = viewModel { ViewLessonViewModel(LessonRequestRepository()) }
) {
    val requests = viewModelInstance.requests
    val isLoading = viewModelInstance.isLoading

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController, userRole = "Tutor")
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BeigeBackground)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "View Lessons",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = PurpleDark,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                textAlign = TextAlign.Center
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (requests.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "You have no pending lesson requests.",
                        style = MaterialTheme.typography.body1,
                        color = PurpleDark
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(requests) { request ->
                        CustomAcceptLessonCard(
                            request = request,
                            onAccept = {
                                viewModelInstance.updateRequestStatus(request.id, "Accepted")
                            },
                            onReject = {
                                viewModelInstance.updateRequestStatus(request.id, "Rejected")
                            }
                        )
                    }
                }
            }
        }
    }
}

