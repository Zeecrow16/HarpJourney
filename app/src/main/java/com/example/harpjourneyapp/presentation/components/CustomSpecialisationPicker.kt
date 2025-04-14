package com.example.harpjourneyapp.presentation.components
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.harpjourneyapp.ui.theme.PurplePrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SpecialisationPicker(
    allOptions: List<String>,
    selectedOptions: List<String>,
    onSelectionChange: (List<String>) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val tempSelections = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.padding(8.dp)) {
        Button(
            onClick = {
                tempSelections.clear()
                tempSelections.addAll(selectedOptions)
                showDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
        ) {
            Text("Choose Specialisations", color = Color.White)
        }

        FlowRow(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            selectedOptions.forEach { tag ->
                Chip(text = tag)
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Select Your Specialisations") },
                text = {
                    Column {
                        allOptions.forEach { option ->
                            val isChecked = tempSelections.contains(option)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { checked ->
                                        if (checked) tempSelections.add(option)
                                        else tempSelections.remove(option)
                                    }
                                )
                                Text(option)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        onSelectionChange(tempSelections.toList())
                        showDialog = false
                    }) {
                        Text("OK", color = PurplePrimary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Discard", color = Color.Gray)
                    }
                }
            )
        }
    }
}
