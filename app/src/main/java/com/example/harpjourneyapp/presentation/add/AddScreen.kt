package com.example.harpjourneyapp.presentation.add

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun AddScreen(text:String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }
}