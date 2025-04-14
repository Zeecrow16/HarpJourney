package com.example.harpjourneyapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harpjourneyapp.enum.HarpType
import com.example.harpjourneyapp.ui.theme.PurplePrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HarpTypeSelector(
    harpTypes: List<HarpType>,
    selectedHarp: HarpType?,
    onHarpSelected: (HarpType) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(
            text = "Which harp do you play?",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            harpTypes.forEach { harpType ->
                FilterChip(
                    selected = harpType == selectedHarp,
                    onClick = { onHarpSelected(harpType) },
                    label = {
                        Text(
                            harpType.name, // Display the enum's name (e.g., "PEDAL")
                            color = if (harpType == selectedHarp) Color.White else PurplePrimary
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PurplePrimary,
                        containerColor = Color(0xFFF0E6F6)
                    )
                )
            }
        }


        selectedHarp?.let {
            Text(
                text = "You've chosen: $it ",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
