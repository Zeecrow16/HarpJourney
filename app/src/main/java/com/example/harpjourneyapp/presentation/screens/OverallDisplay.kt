//package com.example.harpjourneyapp.presentation.screens
//
//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.padding
////noinspection UsingMaterialAndMaterial3Libraries
//import androidx.compose.material.DrawerValue
////noinspection UsingMaterialAndMaterial3Libraries
//import androidx.compose.material.Scaffold
////noinspection UsingMaterialAndMaterial3Libraries
//import androidx.compose.material.rememberDrawerState
//import androidx.compose.material3.ModalDrawerSheet
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.example.harpjourneyapp.R
//import com.example.harpjourneyapp.navigation.NavigationGraph
//import kotlinx.coroutines.launch
//
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun <DrawerState> OverallDisplay(
//    modifier: Modifier = Modifier,
//    navController: NavHostController = rememberNavController(),
//    drawerState: androidx.compose.material.DrawerState = rememberDrawerState(
//        initialValue = DrawerValue.Closed)
//) {
//    val coroutineScope = rememberCoroutineScope()
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = {
//            ModalDrawerSheet {
//                DrawerContent(
//                    menuTitle = stringResource(R.string.menu_name),
//                    navController,
//                    closeDrawer = {
//                        coroutineScope.launch {
//                            drawerState.close()
//                        }
//                    }
//                )
//            }
//        }
//    ) {
//        Scaffold(
//            modifier = modifier,
//            topBar = {
//                TopBar(text = stringResource(R.string.app_name),
//                    onMenuIconClick = {
//                        coroutineScope.launch {
//                            drawerState.open()
//                        }
//                    })
//            }
//        ) { paddingValues ->
//            NavigationGraph(navController = navController,
//                modifier.padding(paddingValues))
//        }
//    }
//}
//
//
