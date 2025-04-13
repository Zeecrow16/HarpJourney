package com.example.harpjourneyapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.harpjourneyapp.ui.theme.GoldDropDown

@Composable
fun CustomDropdownMenu(
    label: String,
    selectedItem: String?,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = label,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(20.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, GoldDropDown, RoundedCornerShape(8.dp))
                    .zIndex(1f)
            ) {
                Text(
                    text = selectedItem?.lowercase() ?: "Select...",
                    modifier = Modifier.align(Alignment.CenterStart),
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                )
            }

            if (expanded) {
                DropdownContent(
                    items = items,
                    onItemSelected = onItemSelected
                )
            }
        }
    }
}

@Composable
fun DropdownContent(items: List<String>, onItemSelected: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
            .offset(y = 10.dp)
            .border(1.dp, GoldDropDown, RoundedCornerShape(8.dp))
            .zIndex(2f)
            .verticalScroll(scrollState)
    ) {
        items.forEach { item ->
            DropdownItem(
                text = item.lowercase(),
                onClick = {
                    onItemSelected(item)
                }
            )
        }
    }
}

@Composable
fun DropdownItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Text(text = text, modifier = Modifier.padding(start = 16.dp))
    }
}
