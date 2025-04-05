package com.example.harpjourneyapp.navigation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.harpjourneyapp.R
import com.example.harpjourneyapp.presentation.add.AddScreen
import com.example.harpjourneyapp.presentation.view_delete.HomeScreen
import kotlin.system.exitProcess

sealed class NavScreen(var icon:Int, var route:String){
    data object Login : NavScreen(R.drawable.homebutton, "Login")
    data object Home : NavScreen(R.drawable.homebutton, "Home")
    data object Add : NavScreen(R.drawable.addbutton, "Add")
    data object Edit: NavScreen(R.drawable.editbutton, "Edit")
    data object Logout : NavScreen(R.drawable.logoutbutton, "Logout")
}

@Composable
fun NavigationGraph(navController: NavHostController,
                    context: Context,
                    modifier: Modifier){
    var selectedContactIndex by remember{ mutableIntStateOf(-1) }

    NavHost(navController,
        startDestination = NavScreen.Login.route){

        composable(NavScreen.Home.route) {
            HomeScreen(
                context = context,
                selectedIndex = selectedContactIndex,
                onIndexChange = {
                        Log.v("OK","index change event called")
                        selectedContactIndex = it
                    },
                onClickToEdit = {
                    if (selectedContactIndex != -1) navController.navigate("edit")
                    else{
                        Toast.makeText(context,
                            context.getString(R.string.no_selection),
                            Toast.LENGTH_LONG).show();
                        }
                    }
                );

        }

        composable(NavScreen.Add.route){
            AddScreen(stringResource(R.string.add_button),
                    modifier)
        }

        composable(NavScreen.Edit.route) {
            EditScreen(selectedContactIndex = selectedContactIndex,
                onClickToHome = {
                    if (selectedContactIndex != -1)
                        navController.popBackStack()
                }
            )
        }
        composable(NavScreen.Logout.route){
            exitProcess(0)
        }
    }
}