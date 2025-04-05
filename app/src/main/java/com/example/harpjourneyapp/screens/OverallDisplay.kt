package com.example.harpjourneyapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.harpjourneyapp.presentation.components.BottomNavBar
import com.example.harpjourneyapp.navigation.NavigationGraph

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OverallDisplay(modifier: Modifier = Modifier,
                   navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current.applicationContext
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        NavigationGraph(navController = navController,
            context,
            modifier.padding(paddingValues))
    }
}



