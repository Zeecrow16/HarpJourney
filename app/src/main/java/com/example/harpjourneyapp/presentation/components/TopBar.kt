package com.example.harpjourneyapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(text: String,
           onMenuIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = text
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                onMenuIconClick()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "navigation"
                )
            }
        }
    )
}