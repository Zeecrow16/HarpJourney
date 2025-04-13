package com.example.harpjourneyapp.presentation.screens.student

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.harpjourneyapp.presentation.components.CustomDropdownMenu
import com.example.harpjourneyapp.ui.theme.BeigeBackground
import com.example.harpjourneyapp.ui.theme.PurplePrimary
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Composable
fun FindTutor() {
    val context = LocalContext.current

    var selectedTutor by remember { mutableStateOf<String?>(null) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

    val tutors = listOf("Alice", "Ben", "Clara", "Dylan")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BeigeBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        CustomDropdownMenu(
            label = "Select Tutor",
            selectedItem = selectedTutor,
            items = tutors,
            onItemSelected = { selectedTutor = it }
        )

        Button(
            onClick = {
                val now = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                    },
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
                ).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
        ) {
            Text(text = selectedDate?.toString() ?: "Select Date")
        }

        Button(
            onClick = {
                val now = Calendar.getInstance()
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        selectedTime = LocalTime.of(hour, minute)
                    },
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true
                ).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
        ) {
            Text(text = selectedTime?.toString() ?: "Select Time")
        }

        Button(
            onClick = {
                println("Lesson Requested: $selectedTutor on $selectedDate at $selectedTime")
            },
            enabled = selectedTutor != null && selectedDate != null && selectedTime != null,
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Request Lesson")
        }
    }
}

@Preview(showBackground = true, name = "Find Tutor Preview")
@Composable
fun FindTutorPreview() {
    FindTutor()
}
