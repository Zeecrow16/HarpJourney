package com.example.harpjourneyapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(text:String,
                 clickButton:()-> Unit){
    Button(
        onClick = clickButton,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(horizontal = 10.dp)
    ){
        Text(text=text, fontSize=20.sp)
    }
}