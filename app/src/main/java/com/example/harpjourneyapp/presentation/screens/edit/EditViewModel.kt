//package com.example.harpjourneyapp.presentation.edit
//
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import com.example.harpjourneyapp.data.Contact
//import com.example.harpjourneyapp.data.ContactRepository
//
//class EditViewModel(private val repo: ContactRepository<Contact>) : ViewModel() {
//    private var contactSelectedByUser : Contact? = NOTHING_IS_CURRENTLY_SELECTED
//    var firstName by mutableStateOf(String())
//    fun firstNameIsValid():Boolean{
//        return firstName.isNotBlank()
//    }
//    var surname by mutableStateOf(String())
//    fun surnameIsValid():Boolean{
//        return surname.isNotBlank()
//    }
//    var telNo by mutableStateOf(String())
//    fun telNoIsValid():Boolean{
//        return telNo.isNotBlank()
//    }
//    fun getContacts(selectedIndex: Int){
//        if(contactSelectedByUser == NOTHING_IS_CURRENTLY_SELECTED) {
//            contactSelectedByUser = repo.findById(selectedIndex)
//            firstName = contactSelectedByUser!!.firstName
//            surname = contactSelectedByUser!!.surname
//            telNo = contactSelectedByUser!!.email
//        }
//    }
//    fun updateContact(){
//        if(firstNameIsValid() && surnameIsValid() && telNoIsValid()) {
//            contactSelectedByUser!!.firstName = firstName
//            contactSelectedByUser!!.surname = surname
//            contactSelectedByUser!!.email = email
//            repo.edit(contactSelectedByUser!!)
//            Log.v("OK", "edited to ${contactSelectedByUser.toString()}")
//        }
//    }
//    companion object {
//        val NOTHING_IS_CURRENTLY_SELECTED: Nothing? = null
//    }
//}