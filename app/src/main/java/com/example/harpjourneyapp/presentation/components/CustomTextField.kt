package com.example.harpjourneyapp.presentation.components

import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Surface
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(hintText: String,
                    text: String,
                    onNameChange: (String) -> Unit,
                    errorMessage: String,
                    errorPresent: Boolean){
    Surface(modifier = Modifier.padding(10.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = onNameChange,
            isError = errorPresent,
            singleLine = true,
            label = {
                Text(hintText)
            }
        )
        Text(
            modifier = Modifier.padding(10.dp),
            text = if (errorPresent) "" else errorMessage,
            fontSize = 14.sp,
            color = Color.Red,
        )
    }
}