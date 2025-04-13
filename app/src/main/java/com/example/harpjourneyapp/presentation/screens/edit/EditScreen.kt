//package com.example.harpjourneyapp.presentation.edit
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.harpjourneyapp.R
//import com.example.harpjourneyapp.ViewModelFactory
//import com.example.harpjourneyapp.presentation.components.CustomButton
//import com.example.harpjourneyapp.presentation.components.CustomTextField
//
//@Composable
//fun EditScreen(vm: EditViewModel = viewModel(factory = ViewModelFactory.Factory),
//               selectedContactIndex:Int,
//               onClickToHome: () -> Unit){
//    vm.getContacts(selectedContactIndex)
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Spacer(modifier = Modifier.size(30.dp))
//        Text(
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            text = "Edit",
//            textAlign = TextAlign.Center,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//        )
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Column {
//                CustomTextField(
//                    stringResource(R.string.first_name_hint),
//                    text = vm.firstName,
//                    onNameChange = { vm.firstName = it },
//                    stringResource(R.string.first_name_error_message),
//                    vm.firstNameIsValid()
//                )
//                CustomTextField(
//                    stringResource(R.string.surname_hint),
//                    text = vm.surname,
//                    onNameChange = { vm.surname = it },
//                    stringResource(R.string.surname_error_message),
//                    vm.surnameIsValid()
//                )
//                CustomTextField(
//                    stringResource(R.string.email_hint),
//                    text = vm.email,
//                    onNameChange = { vm.email = it },
//                    stringResource(R.string.email_error_message),
//                    vm.telNoIsValid()
//                )
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    CustomButton(
//                        stringResource(R.string.edit_button),
//                        clickButton = {
//                            vm.updateContact()
//                            onClickToHome()
//                        })
//                    CustomButton(
//                        stringResource(R.string.home_button),
//                        clickButton = {
//                            onClickToHome()
//                        })
//                }
//            }
//        }
//    }
//}