package com.example.harpjourneyapp.presentation.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
//import com.example.harpjourneyapp.presentation.components.BottomNavBar
import com.example.harpjourneyapp.presentation.components.DatePickerModal
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StudentHomeScreen(navController: NavHostController) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var formattedDate by remember { mutableStateOf<String?>(null) }

    selectedDateMillis?.let {
        val date = Date(it)
        val format = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        formattedDate = format.format(date)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BeigeBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Student Homepage!", modifier = Modifier.padding(16.dp))

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = { showDatePicker = true }) {
                Text("Pick a Date")
            }

            Spacer(modifier = Modifier.height(16.dp))

            formattedDate?.let {
                Text(text = "Selected Date: $it", modifier = Modifier.padding(top = 16.dp))
            }
        }

        if (showDatePicker) {
            DatePickerModal(
                onDateSelected = { dateMillis ->
                    selectedDateMillis = dateMillis
                },
                onDismiss = { showDatePicker = false }
            )
        }

//        BottomNavBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Preview(showBackground = true)
@Composable
fun StudentHomeScreenPreview() {
    StudentHomeScreen(navController = rememberNavController())
}
