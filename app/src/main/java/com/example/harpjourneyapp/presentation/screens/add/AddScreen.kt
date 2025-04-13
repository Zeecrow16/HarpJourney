//package com.example.harpjourneyapp.presentation.add
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.size
////noinspection UsingMaterialAndMaterial3Libraries
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.*
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.harpjourneyapp.ViewModelFactory
//import com.example.harpjourneyapp.presentation.components.CustomButton
//import com.example.harpjourneyapp.presentation.components.CustomTextField
//
//@Composable
//fun AddScreen(
//    vm: AddViewModel = viewModel(factory = ViewModelFactory.Factory),
//    onClickToHome: () -> Unit,
//    modifier: Modifier
//) {
//    val keyboardController = LocalSoftwareKeyboardController.current
//    Column(modifier = Modifier.fillMaxSize()) {
//        Spacer(modifier = Modifier.size(30.dp))
//        Text(
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            text = stringResource(R.string.add),
//            textAlign = TextAlign.Center,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Black,
//        )
//        Column(modifier = Modifier.fillMaxWidth()) {
//            CustomTextField(
//                hintText = stringResource(R.string.first_name_hint),
//                text = vm.firstName,
//                onNameChange = { vm.firstName = it },
//                errorMessage = stringResource(R.string.first_name_error_message),
//                isValid = vm.firstNameIsValid()
//            )
//            CustomTextField(
//                hintText = stringResource(R.string.surname_hint),
//                text = vm.surname,
//                onNameChange = { vm.surname = it },
//                errorMessage = stringResource(R.string.surname_error_message),
//                isValid = vm.surnameIsValid()
//            )
//            CustomTextField(
//                hintText = stringResource(R.string.email_hint),
//                text = vm.email,
//                onNameChange = { vm.email = it },
//                errorMessage = stringResource(R.string.email_error_message),
//                isValid = vm.emailIsValid()
//            )
//            CustomButton(stringResource(R.string.add_button)) {
//                vm.addContact()
//                keyboardController?.hide()
//                onClickToHome()
//            }
//        }
//    }
//}