package com.example.harpjourneyapp.presentation.components.profile

import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.AlertDialog
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.harpjourneyapp.ui.theme.PurplePrimary

@Composable
fun CustomSelectPicker(
    title: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var tempSelection by remember { mutableStateOf(selectedOption) }

    Column(modifier = Modifier.padding(8.dp)) {
        Button(
            onClick = {
                tempSelection = selectedOption
                showDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
                .padding(12.dp)
        ) {
            Text(
                text = selectedOption ?: "Select $title",
                color = Color.White
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Select $title") },
                text = {
                    Column {
                        options.forEach { option ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = tempSelection == option,
                                    onClick = { tempSelection = option }
                                )
                                Text(option)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        tempSelection?.let { onOptionSelected(it) }
                        showDialog = false
                    }) {
                        Text("OK", color = PurplePrimary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel", color = Color.Gray)
                    }
                }
            )
        }
    }
}
