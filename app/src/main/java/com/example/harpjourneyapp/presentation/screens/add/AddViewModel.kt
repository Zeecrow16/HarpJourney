//package com.example.harpjourneyapp.presentation.screens.add
//
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import com.example.harpjourneyapp.data.Contact
//import com.example.harpjourneyapp.data.User
//import java.util.UUID
//
//class AddViewModel(private val repo: ContactRepository<Contact>) : ViewModel() {
//    var firstName by mutableStateOf(String())
//    fun firstNameIsValid():Boolean{
//        return firstName.isNotBlank()
//    }
//    var surname by mutableStateOf(String())
//    fun surnameIsValid():Boolean{
//        return surname.isNotBlank()
//    }
//    var email by mutableStateOf(String())
//    fun emailIsValid():Boolean{
//        return email.isNotBlank()
//    }
//    fun addContact(){
//        if(firstNameIsValid() && surnameIsValid() && emailIsValid()) {
//            var newContact = Contact(
//                UUID.randomUUID(),
//                firstName,
//                surname,
//                email
//            )
//            repo.insert(newContact)
//            Log.v("OK", "added, repo size is now ${repo.findAll().size}")
//            clear()
//        }
//    }
//    private fun clear(){
//        firstName = String()
//        surname=String()
//        email=String()
//    }
//}