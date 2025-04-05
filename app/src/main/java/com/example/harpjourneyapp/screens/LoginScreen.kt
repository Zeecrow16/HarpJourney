package com.example.harpjourneyapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(text:String,
                clickAction: () -> Unit,
                modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Button(
            onClick = clickAction,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(horizontal = 10.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = text, fontSize = 20.sp)
        }
    }
}



