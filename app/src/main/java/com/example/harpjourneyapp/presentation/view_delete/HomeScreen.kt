package com.example.harpjourneyapp.presentation.view_delete

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.harpjourneyapp.R
import com.example.harpjourneyapp.presentation.components.CustomButton

@Composable
fun HomeScreen(
    vm: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    context: Context,
    selectedIndex: Int,
    onIndexChange: (Int) -> Unit,
    onClickToEdit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(30.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.home_button),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )

        Spacer(modifier = Modifier.size(30.dp))

        LazyList(vm, onIndexChange)

        Spacer(modifier = Modifier.size(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CustomButton(stringResource(R.string.edit_button), onClickToEdit)

            CustomButton(stringResource(R.string.delete_button)) {
                if (selectedIndex != -1 && vm.items.isNotEmpty()) {
                    vm.deleteContact()
                    onIndexChange(-1)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.no_selection),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
