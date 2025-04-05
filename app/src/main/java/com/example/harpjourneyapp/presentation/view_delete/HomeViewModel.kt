package com.example.harpjourneyapp.presentation.view_delete

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.harpjourneyapp.data.Contact
import com.example.harpjourneyapp.data.ContactRepository

class HomeViewModel(private val repo: ContactRepository<Contact>): ViewModel() {

    val items = mutableStateListOf<Contact>()
    var selectedContactIndex: Int = NO_SELECTION

    init{
        Log.v("Ok", "Added contacts to repo on init")
        items.addAll(repo.findAll())
    }

    fun deleteContact(){
        val selectedContact = repo.findById(selectedContactIndex)
        repo.delete(selectedContact)
        items.remove(selectedContact)
        selectedContactIndex=NO_SELECTION
        Log.v("Ok", "deleted repo is now ${repo.findAll().size}")

    }
    companion object{
        const val NO_SELECTION = -1
    }

}